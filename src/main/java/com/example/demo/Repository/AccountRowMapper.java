package com.example.demo.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.example.demo.Entity.AccountEntity;

public class AccountRowMapper implements RowMapper<AccountEntity> {

    @Override
    public AccountEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    	AccountEntity account = new AccountEntity();
        account.setId(rs.getString("id"));
        account.setPassword(rs.getString("password"));
        account.setName(rs.getString("name"));
        return account;
    }
}