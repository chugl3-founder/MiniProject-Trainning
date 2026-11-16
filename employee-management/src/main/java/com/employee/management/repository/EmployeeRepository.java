package com.employee.management.repository;

import com.employee.management.model.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContainingIgnoreCase(String name);
}