package com.employee.management.service;

import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service 
public class UtilityService {
    public String formatEmployeeName(String name){
        if(name == null || name.isBlank()){
            return "";
        }
        return name.trim()
                .replaceAll("\\s+", " ")
                .toUpperCase(Locale.ROOT);

    }

    public String generateEmployeeCode(){
        String randomCode = UUID.randomUUID()
                    .toString()
                    .substring(0,8)
                    .toUpperCase(Locale.ROOT);

        return "EMP-" + randomCode;
    }
}
