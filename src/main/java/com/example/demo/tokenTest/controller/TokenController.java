package com.example.demo.tokenTest.controller;

import com.example.demo.common.enums.WebErrCode;
import com.example.demo.common.util.ResultHelper;
import com.example.demo.tokenTest.form.TokenForm;
import io.jsonwebtoken.JwtException;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @RequestMapping(value = "/getToken", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> index(@RequestBody TokenForm tokenForm) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 60*10*1000;
        Date exp = new Date(expMillis);

        String jws = Jwts.builder()
                .setSubject(tokenForm.getId())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();

        Map<String,Object> token = new HashMap<>();
        token.put("token",jws);
//        assert Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals(tokenForm.getId());

        return ResultHelper.returnResult(WebErrCode.err200,token);
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST, consumes = "application/json")
    public Map<String,Object> check(@RequestBody TokenForm tokenForm) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenForm.getToken());
            return ResultHelper.returnResult(WebErrCode.err200,"驗證成功");
            //OK, we can trust this JWT

        } catch (JwtException e) {
            return ResultHelper.returnException(WebErrCode.err403,e.toString());
            //don't trust the JWT!
        }
    }
}
