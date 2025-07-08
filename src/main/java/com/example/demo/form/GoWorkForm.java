package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalTime;

public class GoWorkForm {

    private LocalDate date;        // 日付（input type="date"）
    private LocalTime startTime;   // 出勤時間（input type="time"）

    // --- getter / setter ---

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
