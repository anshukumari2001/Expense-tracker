package com.expenseTracker.expenseTracker.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.expenseTracker.expenseTracker.repository.UserRepository;
import com.expenseTracker.expenseTracker.security.CustomeUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepository userRepository;

    public WebSecurityConfig(
                             UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Updated way to disable CSRF
            .authorizeHttpRequests(authorize -> authorize
                                       .requestMatchers("/login").permitAll()
                                       .anyRequest().authenticated()
                                  )
            .httpBasic(withDefaults());

//         Configure AuthenticationManagerBuilder
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(new CustomeUserDetailService(userRepository)).passwordEncoder(passwordEncoder());

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomeUserDetailService(userRepository);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
