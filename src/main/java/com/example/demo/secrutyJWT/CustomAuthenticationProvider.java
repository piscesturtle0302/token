package com.example.demo.secrutyJWT;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerService customerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 獲得使用者帳號及密碼
        String account = authentication.getName();
        Customer customer = customerService.findCustomer(account);
        // 帳號密碼驗證邏輯
        if (account.equals(customer.getAccount())) {
            // 生成Authentication令牌
            return new UsernamePasswordAuthenticationToken(account,account);
        } else {
            throw new BadCredentialsException("Password error");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
