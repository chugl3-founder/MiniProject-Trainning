package com.employee.management.service;

import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import com.employee.management.repository.DepartmentRepository;
import com.employee.management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    // Get all employees
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Create employee
    public Employee addEmployee(Employee employee) {

        if (employee.getDepartment() != null
                && employee.getDepartment().getId() != null) {

            Long departmentId = employee.getDepartment().getId();

            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Department not found"));

            employee.setDepartment(department);
        }

        return employeeRepository.save(employee);
    }

    // Update employee
    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {

        return employeeRepository.findById(id).map(existingEmployee -> {

            existingEmployee.setName(employeeDetails.getName());
            existingEmployee.setEmail(employeeDetails.getEmail());

            if (employeeDetails.getDepartment() != null
                    && employeeDetails.getDepartment().getId() != null) {

                Long departmentId = employeeDetails.getDepartment().getId();

                Department department = departmentRepository.findById(departmentId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Department not found"));

                existingEmployee.setDepartment(department);
            }

            return employeeRepository.save(existingEmployee);
        });
    }

    // Delete employee
    public boolean deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            return false;
        }

        employeeRepository.deleteById(id);
        return true;
    }

    // Search employee by name
    @Transactional(readOnly = true)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}