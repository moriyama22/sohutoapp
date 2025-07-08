package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // ログインチェック（セッションにユーザー情報がない場合）
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("/login");  // ログイン画面にリダイレクト
            return false;  // 処理中断
        }

        return true; // 通過許可
    }
}
