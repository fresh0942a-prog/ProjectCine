package com.cine.cine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/funcion/disponibilidad/**").permitAll()
                .requestMatchers("/empresa/**").hasRole("ADMIN_GENERAL")
                .requestMatchers("/usuario/**", "/pelicula/**", "/snack/**", "/sala/**", "/funcion/**")
                .hasAnyRole("ADMIN", "ADMIN_GENERAL")
                .anyRequest().permitAll())
                .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("correo")
                .passwordParameter("contrasena")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error"))
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout"))
                .exceptionHandling(ex -> ex.accessDeniedPage("/acceso-denegado"));
        return http.build();
    }
}