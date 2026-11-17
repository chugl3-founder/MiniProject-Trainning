package com.employee.management.service;

import com.employee.management.dto.EmployeeRequest;
import com.employee.management.exception.DuplicateResourceException;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with ID: " + id));
    }

    public Employee addEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail());
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with ID: "
                                        + request.getDepartmentId()));

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = getEmployeeById(id);

        if (employeeRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail());
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found with ID: "
                                        + request.getDepartmentId()));

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {

        Employee employee = getEmployeeById(id);

        employeeRepository.delete(employee);
    }

    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}