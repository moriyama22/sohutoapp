package com.example.demo.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.*;
import com.example.demo.Repository.*;

@Repository
public class AccountDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // IDで1件検索する
    public AccountEntity findById(String id) {
        String sql = "SELECT * FROM account WHERE id = ?";
        List<AccountEntity> result = jdbcTemplate.query(sql, new AccountRowMapper(), id);

        return result.isEmpty() ? null : result.get(0);
    }

}
