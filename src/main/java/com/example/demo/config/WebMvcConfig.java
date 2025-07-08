package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/**")  // すべてに適用
            .excludePathPatterns(
                "/",              // ホーム
                "/login",         // ログイン画面
                "/dologin",       // ログイン処理
                "/menu",		  // メニュー画面
                "/menuback",	  // メニュー画面に戻る
                "/gowork",		  // 出勤画面
                "/gohome",		  // 退勤画面
                "/setting",		  // 設定画面
                "/css/**",        // 静的リソース
                "/js/**"
            );
    }
}
