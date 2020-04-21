package com.example.demo.tokenTest.controller;

import com.example.demo.common.enums.WebErrCode;
import com.example.demo.common.exception.ApiException;
import com.example.demo.common.util.ResultHelper;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.tokenTest.form.TokenForm;
import io.jsonwebtoken.JwtException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Autowired
    private CustomerService customerService = new CustomerService();

    @RequestMapping(value = "/getToken", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> index(@RequestBody TokenForm tokenForm , HttpServletResponse response, Authentication user) {

        Customer customer = customerService.findCustomer(tokenForm.getId());

        if(customer != null) {
            long nowMillis = System.currentTimeMillis() + 60 * 60 * 8;
            Date now = new Date(nowMillis);
            long expMillis = nowMillis + 60 * 1 * 1000;
            Date exp = new Date(expMillis);

            String jws = Jwts.builder()
                    .setSubject(tokenForm.getId())//
                    .setIssuedAt(now)//簽發時間
                    .setExpiration(exp)//到期時間
                    .signWith(key)//加密鑰匙
                    .compact();//生成JWT

            Map<String, Object> token = new HashMap<>();
            token.put("token", jws);
            token.put("user", user);
            token.put("response", response.toString());
//        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals(tokenForm.getId());
            response.setHeader("Authorization", "Bearer " + jws);
            return ResultHelper.returnResult(WebErrCode.err200, token);
        }else{
            return ResultHelper.returnResult(WebErrCode.err401, "查無帳號");
        }
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> check(@RequestBody TokenForm tokenForm ,HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return ResultHelper.returnResult(WebErrCode.err200,"驗證成功");
            //OK, we can trust this JWT

        } catch (JwtException e) {
            return ResultHelper.returnException(WebErrCode.err401,e.getMessage());
            //don't trust the JWT!
        }
    }

}
