package com.example.demo.securityJWT;

import com.example.demo.customer.service.CustomerService;
import com.example.demo.tokenTest.form.TokenForm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;

public class JwtUtil {
    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Autowired
    private static CustomerService customerService = new CustomerService();
    // JWT產生方法
    public static void addAuthentication(HttpServletResponse response, Authentication user) {
        System.out.println(key);

        long nowMillis = System.currentTimeMillis() + 60 * 60 * 8;
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 60 * 1 * 1000;
        Date exp = new Date(expMillis);
        TokenForm tokenForm = new TokenForm();
        List<String> authority = new ArrayList<>();

        for (GrantedAuthority grantedAuthority:user.getAuthorities()) {
            if(!grantedAuthority.getAuthority().equals("")) {
                authority.add(grantedAuthority.getAuthority());
            }
        }

        tokenForm.setAccount(user.getPrincipal().toString());
        tokenForm.setAuthority(authority);
        Map<String,Object> claims = new HashMap<>();
        claims.put("info", tokenForm);

        String jws = Jwts.builder()
                .setClaims(claims)//資料
                .setSubject(tokenForm.getAccount())//id
                .setIssuedAt(now)//簽發時間
                .setExpiration(exp)//到期時間
                .signWith(key)//加密鑰匙
                .compact();//生成JWT

        System.out.println(jws);
        response.setHeader("Authorization" ,"Bearer " + jws);
    }

    // JWT驗證方法
    public static UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        // 從request的header拿回token
        String token = request.getHeader("Authorization");

        // 解析 Token
        try {
            
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
            // 拿用户名
            String user = claims.getSubject();
            
            System.out.println(user);
            // 得到權限
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorize"));
            // 返回Token
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        } catch (JwtException ex) {
            System.out.println(ex);
        }
        
        return null;
    }
}
