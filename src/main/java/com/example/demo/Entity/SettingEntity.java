package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "setting")
public class SettingEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "spreadsheet_url")
    private String spreadsheetUrl;
    
    @Column(name = "sheet_name")
    private String sheetName;
    
    @Column(name = "start_date_cell")
    private String startDateCell;
    
    @Column(name = "start_time_cell")
    private String startTimeCell;
    
    @Column(name = "end_time_cell")
    private String endTimeCell;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // --- ゲッターとセッター ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpreadsheetUrl() {
        return spreadsheetUrl;
    }

    public void setSpreadsheetUrl(String spreadsheetUrl) {
        this.spreadsheetUrl = spreadsheetUrl;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getStartDateCell() {
        return startDateCell;
    }

    public void setStartDateCell(String startDateCell) {
        this.startDateCell = startDateCell;
    }

    public String getStartTimeCell() {
        return startTimeCell;
    }

    public void setStartTimeCell(String startTimeCell) {
        this.startTimeCell = startTimeCell;
    }

    public String getEndTimeCell() {
        return endTimeCell;
    }

    public void setEndTimeCell(String endTimeCell) {
        this.endTimeCell = endTimeCell;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
