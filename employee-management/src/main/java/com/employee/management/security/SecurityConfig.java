package com.employee.management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("roles");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            .authorizeHttpRequests(auth -> auth

                // Các endpoint đăng ký và đăng nhập
                .requestMatchers("/api/auth/**").permitAll()

                // Actuator
                .requestMatchers(
                        "/actuator/health",
                        "/actuator/info"
                ).permitAll()

                .requestMatchers("/actuator/**").hasRole("ADMIN")

                // USER và ADMIN được phép đọc dữ liệu nhân viên
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/employees/**"
                ).hasAnyRole("USER", "ADMIN")

                // Chỉ ADMIN được phép tạo, sửa, xóa
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PATCH,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/employees/**"
                ).hasRole("ADMIN")

                // Các API khác yêu cầu ADMIN
                .requestMatchers("/api/**").hasRole("ADMIN")

                // Giữ các trang Thymeleaf hiện tại truy cập được
                .requestMatchers(
                        "/",
                        "/hello",
                        "/employees/**",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                .anyRequest().permitAll()
            )

            // Hỗ trợ cả Basic Authentication và JWT Bearer
            .httpBasic(Customizer.withDefaults())

            .oauth2ResourceServer(oauth2 ->
                    oauth2.jwt(jwt ->
                            jwt.jwtAuthenticationConverter(
                                    jwtAuthenticationConverter
                            )
                    )
            );

        return http.build();
    }
}