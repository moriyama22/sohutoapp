package com.example.demo.dao;

import com.example.demo.Entity.SettingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class SettingDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    

    public Optional<SettingEntity> findById(String id) {
        String sql = "SELECT * FROM setting WHERE id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), id)
                           .stream()
                           .findFirst(); // Optionalで返す
    }

    public int insert(SettingEntity setting) {
        String sql = "INSERT INTO setting (id, spreadsheet_url, sheet_name, start_date_cell, start_time_cell, end_time_cell) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                setting.getId(),
                setting.getSpreadsheetUrl(),
                setting.getSheetName(),
                setting.getStartDateCell(),
                setting.getStartTimeCell(),
                setting.getEndTimeCell());
    }

    public int update(SettingEntity setting) {
        String sql = "UPDATE setting SET spreadsheet_url = ?, sheet_name = ?, start_date_cell = ?, start_time_cell = ?, end_time_cell = ? " +
                     "WHERE id = ?";
        return jdbcTemplate.update(sql,
                setting.getSpreadsheetUrl(),
                setting.getSheetName(),
                setting.getStartDateCell(),
                setting.getStartTimeCell(),
                setting.getEndTimeCell(),
                setting.getId());
    }

    private SettingEntity mapRow(ResultSet rs) throws SQLException {
    	SettingEntity s = new SettingEntity();
        s.setId(rs.getString("id"));
        s.setSpreadsheetUrl(rs.getString("spreadsheet_url"));
        s.setSheetName(rs.getString("sheet_name"));
        s.setStartDateCell(rs.getString("start_date_cell"));
        s.setStartTimeCell(rs.getString("start_time_cell"));
        s.setEndTimeCell(rs.getString("end_time_cell"));
        s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return s;
    }
    
    public Optional<SettingEntity> findLatestByUserId(String userId) {
        String sql = "SELECT * FROM setting WHERE id = ? ORDER BY created_at DESC LIMIT 1";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), userId)
                           .stream()
                           .findFirst();
    }
}
