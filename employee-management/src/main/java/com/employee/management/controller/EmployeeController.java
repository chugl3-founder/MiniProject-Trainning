package com.employee.management.controller;

import com.employee.management.dto.EmployeeCountReportResponse;
import com.employee.management.dto.EmployeeRequest;
import com.employee.management.model.Employee;
import com.employee.management.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(
                employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        Employee employee = employeeService.addEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String name) {

        return ResponseEntity.ok(
                employeeService.searchEmployeesByName(name));
    }

    @GetMapping({"/count", "/report/count"})
    public ResponseEntity<EmployeeCountReportResponse> getEmployeeCountReport() {
        return ResponseEntity.ok(
                employeeService.getEmployeeCountReport());
    }
}