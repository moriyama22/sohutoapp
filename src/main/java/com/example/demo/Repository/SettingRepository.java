package com.example.demo.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.demo.Entity.SettingEntity;

public interface SettingRepository extends JpaRepository<SettingEntity, String> {
    
	/*
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	//SettingEntity findOneById(String id);
	public SettingEntity findById(String id) {
        String sql = "SELECT * FROM settings WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SettingEntity.class), id);
    }
	
	public void save(SettingEntity entity) {
        String sql = "UPDATE settings SET spreadsheet_url=?, sheet_name=?, start_date_cell=?, start_time_column=?, end_time_column=? WHERE id=?";
        jdbcTemplate.update(sql,
            entity.getSpreadsheetUrl(),
            entity.getSheetName(),
            entity.getStartDateCell(),
            entity.getStartTimeColumn(),
            entity.getEndTimeColumn(),
            entity.getId()
        );
    }
    */
}
