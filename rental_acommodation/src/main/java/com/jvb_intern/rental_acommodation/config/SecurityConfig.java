package com.jvb_intern.rental_acommodation.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((authorize) ->
                authorize
                        .antMatchers("/register/**").permitAll()
                        .antMatchers("/forgot-password").permitAll()
                        .antMatchers("/tenant/**").hasRole("TENANT")
                        .antMatchers("/landlord/**").hasRole("LANDLORD")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(
                form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index")
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority auth : authentication.getAuthorities()) {
                                if (auth.getAuthority().equals("TENANT")) {
                                    response.sendRedirect("/tenant/tenant-home");
                                    return;
                                } else if (auth.getAuthority().equals("LANDLORD")) {
                                    response.sendRedirect("/landlord/landlord-home");
                                    return;
                                } else if (auth.getAuthority().equals("ADMIN")) {
                                    response.sendRedirect("/admin/admin-home");
                                    System.out.println("Redirecting to admin/admin-home");
                                    return;
                                }
                            }
                        }).permitAll()
        )
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

        auth.inMemoryAuthentication()
            .withUser("admin@gmail.com")
            .password(passwordEncoder().encode("123456"))
            .roles("ADMIN");
    }

}
