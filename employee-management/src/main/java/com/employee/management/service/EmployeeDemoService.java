package com.employee.management.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service 
public class EmployeeDemoService {
    
    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeDemoService(UtilityService utilityService, PasswordEncoder passwordEncoder){
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    public String formatEmployeeName(String name) {
        return utilityService.formatEmployeeName(name);
    }

    public String generateEmployeeCode() {
        return utilityService.generateEmployeeCode();
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matchesPassword(
            String rawPassword,
            String encodedPassword) {

        return passwordEncoder.matches(
                rawPassword,
                encodedPassword
        );
    }
}
