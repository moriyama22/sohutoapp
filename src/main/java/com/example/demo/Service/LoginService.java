package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountDao;
import com.example.demo.Entity.AccountEntity;
import com.example.demo.Repository.AccountRowMapper;

@Service
public class LoginService {
	
	private final AccountDao accountDao;
	
	public LoginService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
	
	public AccountEntity login(String id, String password) {
        // IDに対応するユーザーをDBから取得
		AccountEntity account = accountDao.findById(id);

        if (account == null) {
            // IDが存在しない
            throw new RuntimeException("ユーザーが見つかりません");
        }

        if (!account.getPassword().equals(password)) {
            // パスワード不一致（本番ではハッシュと比較）
            throw new RuntimeException("パスワードが間違っています");
        }

        // ログイン成功
        return account;
    }

}
