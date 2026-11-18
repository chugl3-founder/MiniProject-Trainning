package com.employee.management.dto;

import java.util.List;

public class EmployeeStatisticsDto {

    private long totalEmployees;
    private long totalDepartments;
    private List<DepartmentEmployeeCountDto> departmentStats;

    public EmployeeStatisticsDto() {
    }

    public EmployeeStatisticsDto(long totalEmployees, long totalDepartments, List<DepartmentEmployeeCountDto> departmentStats) {
        this.totalEmployees = totalEmployees;
        this.totalDepartments = totalDepartments;
        this.departmentStats = departmentStats;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public List<DepartmentEmployeeCountDto> getDepartmentStats() {
        return departmentStats;
    }

    public void setDepartmentStats(List<DepartmentEmployeeCountDto> departmentStats) {
        this.departmentStats = departmentStats;
    }
}
