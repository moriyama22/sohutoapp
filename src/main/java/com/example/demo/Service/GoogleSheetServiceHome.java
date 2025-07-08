package com.example.demo.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.example.demo.Entity.SettingEntity;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

@Service
public class GoogleSheetServiceHome {
	
    private static final String APPLICATION_NAME = "退勤システム";
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json"; // resources直下	
    
 // Google Sheets APIのクライアントを生成
    private Sheets getSheetsService() throws Exception {
        String json = System.getenv("GOOGLE_CREDENTIALS_JSON");
        if (json == null || json.isEmpty()) {
            throw new Exception("環境変数 'GOOGLE_CREDENTIALS_JSON' が設定されていません。");
        }

        InputStream credentialsStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        )
        .setApplicationName(APPLICATION_NAME)
        .build();
    }
    
    /**
     * 退勤時間をスプレッドシートに登録
     * @param setting 設定情報
     * @param date 入力された日付
     * @param endTime 退勤時間 (例: "08:30")
     */
    public void writeStartTime(SettingEntity setting, LocalDate date, String endTime) throws Exception {
        Sheets sheetsService = getSheetsService();

        // スプレッドシートIDをURLから取得
        String spreadsheetId = extractSpreadsheetId(setting.getSpreadsheetUrl());

        // シート名
        String sheetName = setting.getSheetName();

        // 行番号の算出：start_date_cell を起点として日数分下へ移動
        int baseRow = getRowNumber(setting.getStartDateCell());
        int offset = date.getDayOfMonth() - 1;  // 1日→0行目オフセット
        int targetRow = baseRow + offset;

        // 書き込むセルの位置（列部分はstart_time_cellを使う）
        String column = getColumnLetter(setting.getEndTimeCell());
        String targetCell = sheetName + "!" + column + targetRow;

        // 書き込みデータ
        ValueRange body = new ValueRange()
                .setValues(List.of(List.of(endTime))); // 二次元リストでラップする必要あり

        sheetsService.spreadsheets().values()
                .update(spreadsheetId, targetCell, body)
                .setValueInputOption("RAW")
                .execute();
    }

    // A1形式のセル位置から列記号だけを抜き出す（例：B5 → B）
    private String getColumnLetter(String cell) {
        return cell.replaceAll("[0-9]", "");
    }

    // A1形式のセル位置から行番号を抜き出す（例：B5 → 5）
    private int getRowNumber(String cell) {
        return Integer.parseInt(cell.replaceAll("[^0-9]", ""));
    }

    // スプレッドシートURLからID部分だけを抽出する
    private String extractSpreadsheetId(String url) throws Exception {
        String regex = "/spreadsheets/d/([a-zA-Z0-9-_]+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new Exception("スプレッドシートIDがURLから抽出できませんでした: " + url);
        }
    }
    
    public void writeCell(String spreadsheetUrl, String sheetName, String cell, String value) throws Exception {
        Sheets sheetsService = getSheetsService();

        String spreadsheetId = extractSpreadsheetId(spreadsheetUrl);
        String range = sheetName + "!" + cell;

        ValueRange body = new ValueRange()
                .setValues(List.of(List.of(value)));

        sheetsService.spreadsheets().values()
                .update(spreadsheetId, range, body)
                .setValueInputOption("RAW")
                .execute();
    }

}
