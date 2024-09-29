package com.beehyv.SpringSecurity.config;

import com.beehyv.SpringSecurity.entity.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(registry -> {
            registry.requestMatchers("/register/**").permitAll();
            registry.requestMatchers("/home").permitAll();
            registry.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN");
            registry.requestMatchers("/admin/**").hasRole("ADMIN");
            registry.anyRequest().authenticated();
        })
        .formLogin(login -> {
            login.permitAll().successHandler(successHandler);
        })
        .csrf(AbstractHttpConfigurer::disable)
        .build();
    }

    // In-memory users
    /*
        @Bean
        public UserDetailsService userDetailsService() {
            UserDetails normalUser = User.builder().username("normal").password("$2a$12$T6IxAh/v2.iv9kLwZ9r1JOoYee1vZgBKXPaWxRauhQD9elxn1ZMGG").roles("USER").build();
            UserDetails adminUser = User.builder().username("admin").password("$2a$12$pGfDTRbLF7kE1fnFCM6d5efWdYK.YNydcsAYC66bOXaCFZeyaUPLe").roles("ADMIN", "USER").build();

            return new InMemoryUserDetailsManager(normalUser, adminUser);
        }
    */

    // Database users
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
