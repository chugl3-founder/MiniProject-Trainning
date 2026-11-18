package com.employee.management.dto;

public class DepartmentEmployeeCountDto {

    private String departmentName;
    private Long employeeCount;

    public DepartmentEmployeeCountDto() {
    }

    public DepartmentEmployeeCountDto(String departmentName, Long employeeCount) {
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
