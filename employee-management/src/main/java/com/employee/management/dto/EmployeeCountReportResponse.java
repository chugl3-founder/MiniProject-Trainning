package com.employee.management.dto;

import java.time.LocalDateTime;

public class EmployeeCountReportResponse {

    private long totalEmployees;
    private LocalDateTime generatedAt;
    private String note;

    public EmployeeCountReportResponse() {
    }

    public EmployeeCountReportResponse(long totalEmployees, LocalDateTime generatedAt, String note) {
        this.totalEmployees = totalEmployees;
        this.generatedAt = generatedAt;
        this.note = note;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
