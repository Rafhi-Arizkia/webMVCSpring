package com.example.webmvcspring.security;

import com.example.webmvcspring.service.impl.AccountUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AccountUserServiceImpl accountService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(AccountUserServiceImpl accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/login/**", "/register/**",
                                "/denied", "/video/*.mp4")
                        .permitAll()
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .formLogin((login) -> login
                        .loginPage("/login")
                        .successHandler(new AuthenticationSuccess())
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .usernameParameter("email")
                        .passwordParameter("password"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .permitAll())
                .authenticationProvider(getAuthenticationProvider())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(accountService);
        return auth;
    }
}
