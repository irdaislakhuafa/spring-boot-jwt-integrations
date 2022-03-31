package com.irdaislakhuafa.springbootjwtintegrations.security;

import com.irdaislakhuafa.springbootjwtintegrations.services.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class MyWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MySecurityFilter mySecurityFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // disable form login
                .authorizeRequests()

                .antMatchers("/v2/auth/**").permitAll()
                .anyRequest().fullyAuthenticated()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session

                .and()
                .addFilterBefore(mySecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
