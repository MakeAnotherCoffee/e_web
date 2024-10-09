package com.mwas.securityjwt106.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigarations {
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    private UserDetails userDetails;
    @Autowired
    private SuccessHandler successHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/signin")
                        .successHandler(successHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .permitAll())

                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/admin/**","/admin/addfiles").hasRole("ADMIN");
                    registry.requestMatchers("/download_file").hasRole("USER");
                    registry.requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll();
                    registry.requestMatchers("/registers","/","/deleteFile","/signin","/signup","/send","/addFile","/getAllFiles","/user/**","/allUsers","/test").permitAll();
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
