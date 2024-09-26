package com.mwas.securityjwt106.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.Customizer;

@EnableWebSecurity
@Configuration
public class SecurityConfigarations {
    @Autowired
    private UserDetails userDetails;
    @Autowired
    private SuccessHandler successHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/signin")
                        .successHandler(successHandler)

                        .permitAll())

                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/admin/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**","/download_file").hasRole("USER");
                    registry.requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll();
                    registry.requestMatchers("/registers","/","/addFile","/deleteFile","/signin","/signup").permitAll();
                    registry.anyRequest().authenticated();
                }).build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetails);
        provider.setPasswordEncoder(passwordEncoder());
        return  provider;

    }
}
