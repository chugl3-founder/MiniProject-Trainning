package com.employee.management.service;

import com.employee.management.dto.EmployeeRequest;
import com.employee.management.exception.DuplicateResourceException;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        log.debug("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        log.debug("Fetching employee by id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException(
                            "Employee not found with ID: " + id);
                });
    }

    public Employee addEmployee(EmployeeRequest request) {
        log.info("Adding new employee with email: {}", request.getEmail());

        if (employeeRepository.existsByEmail(request.getEmail())) {
            log.warn("Failed to add employee: Email already exists '{}'", request.getEmail());
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail());
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    log.warn("Failed to add employee: Department not found with ID: {}", request.getDepartmentId());
                    return new ResourceNotFoundException(
                            "Department not found with ID: "
                                    + request.getDepartmentId());
                });

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully added employee with ID: {}, name: '{}'", savedEmployee.getId(), savedEmployee.getName());
        return savedEmployee;
    }

    public Employee updateEmployee(Long id, EmployeeRequest request) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = getEmployeeById(id);

        if (employeeRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {
            log.warn("Failed to update employee ID {}: Email already exists '{}'", id, request.getEmail());
            throw new DuplicateResourceException(
                    "Email already exists: " + request.getEmail());
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() -> {
                    log.warn("Failed to update employee ID {}: Department not found with ID: {}", id, request.getDepartmentId());
                    return new ResourceNotFoundException(
                            "Department not found with ID: "
                                    + request.getDepartmentId());
                });

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Successfully updated employee with ID: {}", updatedEmployee.getId());
        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);

        Employee employee = getEmployeeById(id);

        employeeRepository.delete(employee);
        log.info("Successfully deleted employee with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        log.debug("Searching employees by name containing: '{}'", name);
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        log.debug("Fetching all departments");
        return departmentRepository.findAll();
    }
}