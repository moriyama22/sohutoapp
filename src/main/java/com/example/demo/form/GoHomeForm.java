package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoHomeForm {
	
	private LocalDate date;        // 日付（input type="date"）
    private LocalTime endTime;   // 出勤時間（input type="time"）

    // --- getter / setter ---

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
