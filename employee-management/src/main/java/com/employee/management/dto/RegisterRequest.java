package com.employee.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50,
                message = "Username must contain 3-50 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 72,
                message = "Password must contain 8-72 characters")
        String password

) {
}