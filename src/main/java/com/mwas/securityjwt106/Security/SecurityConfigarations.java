package com.mwas.securityjwt106.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigarations {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .formLogin(httpSecurityFormLoginConfigurer ->httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .permitAll())
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**");
                    registry.requestMatchers("/","/home").permitAll();
                }).build();
    }
}
