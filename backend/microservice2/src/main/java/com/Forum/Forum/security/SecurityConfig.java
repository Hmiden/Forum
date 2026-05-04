package com.Forum.Forum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public read access
                .requestMatchers(HttpMethod.GET, "/topic/**", "/category/**", "/reply/**").permitAll()
                
                // ADMIN only endpoints
                .requestMatchers(HttpMethod.POST, "/category/**", "/badword/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/category/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/category/**", "/topic/**", "/reply/**", "/badword/**").hasRole("ADMIN")
                .requestMatchers("/report/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/badword/**").hasRole("ADMIN")
                
                // USER (and ADMIN) endpoints
                .requestMatchers(HttpMethod.POST, "/topic/**", "/reply/**", "/report/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/topic/**", "/reply/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
