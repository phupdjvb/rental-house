package com.jvb_intern.rental_acommodation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jvb_intern.rental_acommodation.common.Constant;
import com.jvb_intern.rental_acommodation.service.impl.CustomerUserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomerUserDetailServiceImpl customerUserDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        // .csrf().disable()
            .authorizeHttpRequests((authorize) -> authorize
                .antMatchers(
                        "/resources/**",
                        "/uploaded_images/**",
                        "/static/**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/vendor/**")
                .permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/rules").permitAll()
                .antMatchers("/reset-password/**").permitAll()
                .antMatchers("/forgot-password/**").permitAll()
                .antMatchers("/tenant/**").hasAuthority(Constant.ROLE_TENANT)
                .antMatchers("/landlord/**").hasAuthority(Constant.ROLE_LANDLORD)
                .antMatchers("/admin/**").hasAuthority(Constant.ROLE_ADMIN)
                .anyRequest().authenticated()).formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/index")
                                .successHandler((request, response, authentication) -> {
                                    for (GrantedAuthority auth : authentication.getAuthorities()) {
                                        if (auth.getAuthority().equals(Constant.ROLE_TENANT)) {
                                            response.sendRedirect("/tenant/tenant-home");
                                        } else if (auth.getAuthority().equals(Constant.ROLE_LANDLORD)) {
                                            response.sendRedirect("/landlord/landlord-home");
                                        } else if (auth.getAuthority().equals(Constant.ROLE_ADMIN)) {
                                            response.sendRedirect("/admin/admin-home");
                                        }
                                    }
                                }).permitAll())
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
}
