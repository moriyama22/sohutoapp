package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.Service.*;
import com.example.demo.Entity.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	//private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	//@Autowired
	//private AccountRepository AccountRepository;
	
	@Autowired
	private LoginService LoginService;
	
	@Autowired
	HttpSession SessionEntity;
	
	@GetMapping("/")
    public String home() {
        return "login"; // templates/home.html
    }
	
	
	//ログイン画面
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    
    //ログイン処理
    @PostMapping("/dologin")
    public String login(
    		@RequestParam String id,
            @RequestParam String password,
            Model model
    		) {
	    // 入力チェック
	    if (id.isEmpty() || password.isEmpty()) {
	        model.addAttribute("errorMessage", "IDとパスワードを入力してください");
	        return "login";
	    }
	
	    try {
	    	AccountEntity account = LoginService.login(id, password);
	    	SessionEntity.setAttribute("loginUser", account);
	    	
	        // 成功したらメニューへ
	        model.addAttribute("name", account.getName());
	        return "menu";  // menu.html に遷移
	    } catch (RuntimeException e) {
	        // エラーがあればログイン画面に戻す
	        model.addAttribute("errorMessage", e.getMessage());
	        return "login";
	    }
    	
    }
    		

    

}
