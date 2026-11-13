package com.employee.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.service.EmployeeDemoService;

@RestController 
@RequestMapping("/api/demo")
public class EmployeeDemoController {
    private final EmployeeDemoService employeeDemoService;

    public EmployeeDemoController(EmployeeDemoService employeeDemoService){
        this.employeeDemoService = employeeDemoService;
    }

    @GetMapping("/employee-name")
    public String formatEmployeeName(@RequestParam String name){
        return employeeDemoService.formatEmployeeName(name);
    }

    @GetMapping("employee-code")
    public String generateEmployeeCode(){
        return employeeDemoService.generateEmployeeCode();
    }
}
