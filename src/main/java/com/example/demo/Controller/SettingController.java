package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.*;
import com.example.demo.dao.*;

import jakarta.servlet.http.HttpSession;


@Controller
public class SettingController {
	
	@Autowired
	private SettingDao settingDao;
	
	@Autowired
	HttpSession SessionEntity;

	
	@GetMapping("/setting")
	public String showSettingPage(Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		if (loginUser == null) {
	        // ログインしていない → ログイン画面に戻す
	        return "redirect:/login";
	    }
		

		model.addAttribute("setting", new SettingEntity());
	    return "setting"; // templates/menu.html
	}
	
	@PostMapping("/settingsubmit")
	public String settingsubmit(@ModelAttribute SettingEntity setting, Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		 String userId = loginUser.getId();
		    setting.setId(userId);
		
		 Optional<SettingEntity> existing = settingDao.findById(userId);
		    if (existing.isPresent()) {
		        settingDao.update(setting);
		    } else {
		    	
		        settingDao.insert(setting);
		    }
		    model.addAttribute("setting", setting);
		    model.addAttribute("message", "設定を保存しました");
		    return "setting";
		}

}
