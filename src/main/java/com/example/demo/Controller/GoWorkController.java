package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.AccountEntity;
import com.example.demo.Entity.SettingEntity;
import com.example.demo.Service.GoogleSheetService;
import com.example.demo.dao.SettingDao;
import com.example.demo.form.GoWorkForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class GoWorkController {
	
	@Autowired
	HttpSession SessionEntity;
	
	@Autowired
    private SettingDao settingDao;

    @Autowired
    private GoogleSheetService sheetService;
	
	@GetMapping("/gowork")
	public String showGoworkPage(Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		if (loginUser == null) {
	        // ログインしていない → ログイン画面に戻す
	        return "redirect:/login";
	    }
		
	    return "gowork"; // templates/menu.html
	}
	
	@PostMapping("/goworksubmit")
	public String goworksubmit(@ModelAttribute GoWorkForm form, Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		if (loginUser == null) {
	        // ログインしていない → ログイン画面に戻す
	        return "redirect:/login";
	    }
		
		// Step 1: ログインIDで設定取得
        Optional<SettingEntity> settingOpt = settingDao.findById(loginUser.getId());
        if (settingOpt.isEmpty()) {
            model.addAttribute("error", "設定情報が見つかりません");
            return "gowork";
        }
        
        SettingEntity setting = settingOpt.get();
        String url = setting.getSpreadsheetUrl();
        String sheetName = setting.getSheetName();  // 例："7月"
        String startDateCell = setting.getStartDateCell(); // 例："A8"
        String startTimeCell = setting.getStartTimeCell(); // 例："C8"
		
     // Step 2: 入力された日付の "月" が一致するか確認
        LocalDate date = form.getDate();
        int day = date.getDayOfMonth();
        String inputMonth = date.format(DateTimeFormatter.ofPattern("M月"));
        if (!sheetName.equals(inputMonth)) {
            model.addAttribute("error", "設定されたシート名と日付の月が一致しません");
            return "gowork";
        }
        
     // Step 3: セル位置を動的に計算
        int startRow = Integer.parseInt(startDateCell.replaceAll("[^0-9]", ""));
        String col = startTimeCell.replaceAll("[0-9]", "");
        int row = startRow + (day - 1); // 日に応じて下に移動
        String targetCell = col + row; // 例："C10"
        
     // Step 4: Google Sheets に出勤時間を書き込み
        try {
	        String timeStr = form.getStartTime().toString(); // HH:mm 形式
	        sheetService.writeCell(url, sheetName, targetCell, timeStr);
	
	        model.addAttribute("message", "出勤時間を登録しました");
        } catch (Exception e) {
        	e.printStackTrace();
            model.addAttribute("error", "Google Sheetsへの登録中にエラーが発生しました");
            return "gowork";
        }
		
	    return "gowork"; // templates/menu.html
	}
	
	
	


}
