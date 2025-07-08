package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Service.*;
import com.example.demo.Entity.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {
	
	@Autowired
	HttpSession SessionEntity;
	
	@GetMapping("/menu")
	public String showMenuPage(Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		if (loginUser == null) {
	        // ログインしていない → ログイン画面に戻す
	        return "redirect:/login";
	    }
		
	    return "menu"; // templates/menu.html
	}
	
	@PostMapping("/menuback")
	public String menuback(Model model) {
		
		AccountEntity loginUser = (AccountEntity) SessionEntity.getAttribute("loginUser");
		
		if (loginUser == null) {
	        // ログインしていない → ログイン画面に戻す
	        return "redirect:/login";
	    }
		AccountEntity account = loginUser;
		
		model.addAttribute("name", account.getName());
	    return "menu"; // templates/menu.html
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		SessionEntity.invalidate();
	    return "redirect:/login"; // templates/menu.html
	}

}
