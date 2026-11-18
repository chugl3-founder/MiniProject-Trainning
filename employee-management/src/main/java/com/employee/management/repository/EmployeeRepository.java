package com.employee.management.repository;

import com.employee.management.dto.DepartmentEmployeeCountDto;
import com.employee.management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    // Module 10: Thong ke tong so nhan vien
    @Query("SELECT COUNT(e) FROM Employee e")
    long countTotalEmployees();

    // Module 10: Thong ke so luong nhan vien theo tung phong ban
    @Query("SELECT new com.employee.management.dto.DepartmentEmployeeCountDto(d.name, COUNT(e)) " +
           "FROM Department d LEFT JOIN Employee e ON e.department = d " +
           "GROUP BY d.id, d.name " +
           "ORDER BY COUNT(e) DESC, d.name ASC")
    List<DepartmentEmployeeCountDto> countEmployeesByDepartment();
}