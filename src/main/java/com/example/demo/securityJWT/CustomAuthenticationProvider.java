package com.example.demo.securityJWT;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerService customerService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 獲得使用者帳號及密碼
        String account = authentication.getName();
        String password = authentication.getCredentials().toString();
        Customer customer = customerService.findCustomer(account);
        String encoderPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        // 帳號密碼驗證邏輯
        if (account.equals(customer.getAccount()) && encoderPassword.equals(customer.getPassword())) {
            // 生成Authentication令牌
            return new UsernamePasswordAuthenticationToken(account, password);
        } else {
            throw new BadCredentialsException("Password error");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
