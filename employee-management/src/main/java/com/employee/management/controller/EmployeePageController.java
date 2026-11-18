package com.employee.management.controller;

import com.employee.management.dto.EmployeeRequest;
import com.employee.management.exception.DuplicateResourceException;
import com.employee.management.exception.ResourceNotFoundException;
import com.employee.management.model.Employee;
import com.employee.management.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeePageController {

    private final EmployeeService employeeService;

    public EmployeePageController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Display employee list
    @GetMapping
    public String listEmployees(
            @RequestParam(required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute(
                    "employees",
                    employeeService.searchEmployeesByName(keyword)
            );
        } else {
            model.addAttribute(
                    "employees",
                    employeeService.getAllEmployees()
            );
        }

        model.addAttribute("keyword", keyword);

        return "employees/list";
    }

    // Module 10: Hien thi trang thong ke bao cao
    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        model.addAttribute(
                "statistics",
                employeeService.getEmployeeStatistics()
        );
        model.addAttribute("pageTitle", "Employee Statistics & Analytics");

        return "employees/statistics";
    }

    // Display create form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeRequest());
        model.addAttribute(
                "departments",
                employeeService.getAllDepartments()
        );

        model.addAttribute("pageTitle", "Add Employee");

        return "employees/form";
    }

    // Handle create form submission
    @PostMapping
    public String createEmployee(
            @Valid @ModelAttribute("employee") EmployeeRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Add Employee");

            return "employees/form";
        }

        try {
            employeeService.addEmployee(request);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Employee created successfully!"
            );

            return "redirect:/employees";

        } catch (DuplicateResourceException ex) {
            bindingResult.rejectValue(
                    "email",
                    "duplicate",
                    ex.getMessage()
            );

            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Add Employee");

            return "employees/form";

        } catch (ResourceNotFoundException ex) {
            bindingResult.rejectValue(
                    "departmentId",
                    "notFound",
                    ex.getMessage()
            );

            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Add Employee");

            return "employees/form";
        }
    }

    // Display edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Employee employee = employeeService.getEmployeeById(id);

        EmployeeRequest request = new EmployeeRequest();
        request.setName(employee.getName());
        request.setEmail(employee.getEmail());
        request.setDepartmentId(
                employee.getDepartment().getId()
        );

        model.addAttribute("employee", request);
        model.addAttribute("employeeId", id);
        model.addAttribute(
                "departments",
                employeeService.getAllDepartments()
        );
        model.addAttribute("pageTitle", "Edit Employee");

        return "employees/form";
    }

    // Handle update form submission
    @PostMapping("/{id}/edit")
    public String updateEmployee(
            @PathVariable Long id,
            @Valid @ModelAttribute("employee") EmployeeRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeId", id);
            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Edit Employee");

            return "employees/form";
        }

        try {
            employeeService.updateEmployee(id, request);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Employee updated successfully!"
            );

            return "redirect:/employees";

        } catch (DuplicateResourceException ex) {
            bindingResult.rejectValue(
                    "email",
                    "duplicate",
                    ex.getMessage()
            );

            model.addAttribute("employeeId", id);
            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Edit Employee");

            return "employees/form";

        } catch (ResourceNotFoundException ex) {
            bindingResult.reject(
                    "notFound",
                    ex.getMessage()
            );

            model.addAttribute("employeeId", id);
            model.addAttribute(
                    "departments",
                    employeeService.getAllDepartments()
            );
            model.addAttribute("pageTitle", "Edit Employee");

            return "employees/form";
        }
    }

    // Handle employee deletion
    @PostMapping("/{id}/delete")
    public String deleteEmployee(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            employeeService.deleteEmployee(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Employee deleted successfully!"
            );

        } catch (ResourceNotFoundException ex) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
        }

        return "redirect:/employees";
    }

    // Handle employee not found in MVC pages
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(
            ResourceNotFoundException ex,
            Model model) {

        model.addAttribute("message", ex.getMessage());

        return "error/404";
    }
}