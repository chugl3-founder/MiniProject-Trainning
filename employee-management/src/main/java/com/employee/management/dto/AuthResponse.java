package com.employee.management.dto;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}