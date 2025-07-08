package com.example.demo.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;

import com.example.demo.Entity.SettingEntity;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.auth.oauth2.GoogleCredentials; // ✅ 追加
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;               // ✅ 追加
import java.io.InputStream;
import java.nio.charset.StandardCharsets;          // ✅ 追加
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetService {

    private static final String APPLICATION_NAME = "出勤システム";

    // ✅ 修正：Google Sheets APIのクライアントを生成（環境変数から認証）
    private Sheets getSheetsService() throws Exception {
        // 環境変数から JSON を取得
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
                new HttpCredentialsAdapter(credentials) // ✅ ここも変更
        )
        .setApplicationName(APPLICATION_NAME)
        .build();
    }

    // （以下の処理はそのままでOK）
    public void writeStartTime(SettingEntity setting, LocalDate date, String startTime) throws Exception {
        Sheets sheetsService = getSheetsService();

        String spreadsheetId = extractSpreadsheetId(setting.getSpreadsheetUrl());
        String sheetName = setting.getSheetName();

        int baseRow = getRowNumber(setting.getStartDateCell());
        int offset = date.getDayOfMonth() - 1;
        int targetRow = baseRow + offset;

        String column = getColumnLetter(setting.getStartTimeCell());
        String targetCell = sheetName + "!" + column + targetRow;

        ValueRange body = new ValueRange()
                .setValues(List.of(List.of(startTime)));

        sheetsService.spreadsheets().values()
                .update(spreadsheetId, targetCell, body)
                .setValueInputOption("RAW")
                .execute();
    }

    private String getColumnLetter(String cell) {
        return cell.replaceAll("[0-9]", "");
    }

    private int getRowNumber(String cell) {
        return Integer.parseInt(cell.replaceAll("[^0-9]", ""));
    }

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
