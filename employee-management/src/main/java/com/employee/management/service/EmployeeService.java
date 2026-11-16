package com.employee.management.service;

import com.employee.management.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    private final AtomicLong idGenerator = new AtomicLong(0);

    public EmployeeService() {
        employees.add(
                new Employee(
                        idGenerator.incrementAndGet(),
                        "Nguyen Van A",
                        "nguyenvana@gmail.com"
                )
        );

        employees.add(
                new Employee(
                        idGenerator.incrementAndGet(),
                        "Tran Thi B",
                        "tranthib@gmail.com"
                )
        );
    }

    public List<Employee> getAllEmployees() {
        return List.copyOf(employees);
    }

    public Employee addEmployee(Employee employee) {
        employee.setId(idGenerator.incrementAndGet());

        employees.add(employee);

        return employee;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    public List<Employee> searchEmployeesByName(String name) {
        return employees.stream()
                .filter(employee -> employee.getName() != null)
                .filter(employee -> employee.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }
}