package com.example.demo.securityJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    // 設置HTTP請求驗證
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .cors()
                .and()
            .csrf()
                .disable() // 因為是JWT，無須csrf驗證            
            // 對請求進行驗證
            .authorizeRequests()
                .antMatchers("/index.html", "/Test/hello").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilter(new JWTLoginFilter(authenticationManager()))
            .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定義的驗證
        auth.authenticationProvider(customAuthenticationProvider);
    }
}
