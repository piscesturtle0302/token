package com.example.demo.tokenTest.controller;

import com.example.demo.common.enums.WebErrCode;
import com.example.demo.common.util.ResultHelper;
import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.tokenTest.form.TokenForm;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

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
    public Map<String,Object> index(@RequestBody TokenForm tokenForm , HttpServletResponse response) {

        Customer customer = customerService.findCustomer(tokenForm.getAccount());

        if(customer != null) {
            long nowMillis = System.currentTimeMillis() + 60 * 60 * 8;
            Date now = new Date(nowMillis);
            long expMillis = nowMillis + 60 * 1 * 1000;
            Date exp = new Date(expMillis);

            tokenForm.setAccount(customer.getAccount());
            tokenForm.setName(customer.getLocalName());
            Map<String,Object> claims = new HashMap<>();
            claims.put("info",tokenForm);

            String jws = Jwts.builder()
                    .setClaims(claims)//資料
                    .setSubject(tokenForm.getAccount())//id
                    .setIssuedAt(now)//簽發時間
                    .setExpiration(exp)//到期時間
                    .signWith(key)//加密鑰匙
                    .compact();//生成JWT


//        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals(tokenForm.getId());
            response.setHeader("Authorization", "Bearer " + jws);
            return ResultHelper.returnResult(WebErrCode.err200,"使用者登入");
        }else{
            return ResultHelper.returnResult(WebErrCode.err401, "查無帳號");
        }
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> check(@RequestBody TokenForm tokenForm ,HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            Claims jwsMap = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            return ResultHelper.returnResult(WebErrCode.err200,"驗證成功 : " + jwsMap.get("info"));
            //OK, we can trust this JWT

        } catch (JwtException e) {
            return ResultHelper.returnException(WebErrCode.err401,e.getMessage());
            //don't trust the JWT!
        }
    }

}
