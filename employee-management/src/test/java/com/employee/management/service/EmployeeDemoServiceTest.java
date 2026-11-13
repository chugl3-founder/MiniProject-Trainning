package com.employee.management.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeDemoServiceTest {

    @Autowired
    private EmployeeDemoService employeeDemoService;

    @Test
    void shouldEncodeAndMatchPassword() {
        String rawPassword = "123456";

        String encodedPassword =
                employeeDemoService.encodePassword(rawPassword);

        assertNotEquals(rawPassword, encodedPassword);

        assertTrue(
                employeeDemoService.matchesPassword(
                        rawPassword,
                        encodedPassword
                )
        );

        assertFalse(
                employeeDemoService.matchesPassword(
                        "wrong-password",
                        encodedPassword
                )
        );
    }
}