package com.example.lab3.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Value("${security.logins}")
    private String[] logins;

    @Value("${security.passwords}")
    private String[] passwords;

    @Value("${security.roles}")
    private String[] roles;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/projects/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/projects").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/projects/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/projects", true) // Перенаправление после логина
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                ).csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        for (int i = 0; i < logins.length; i++) {
            manager.createUser(User.withUsername(logins[i])
                    .password(passwordEncoder().encode(passwords[i]))
                    .authorities("ROLE_" + roles[i])
                    .build());
        }
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
