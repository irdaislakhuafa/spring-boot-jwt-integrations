package com.irdaislakhuafa.springbootjwtintegrations.security;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.irdaislakhuafa.springbootjwtintegrations.models.entities.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtProvider implements BaseJwtProvider {

    // key algorithm
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // time expirated
    private Long expiration = (1000L * 60 * 60 * 24); // 1 days

    @Override
    public String generateToken(Authentication authentication) {
        try {
            // get current user
            final User user = (User) authentication.getPrincipal();

            // current time
            Date now = new Date(System.currentTimeMillis());
            Date expiratedDate = new Date(now.getTime() + (expiration));

            // set claims body
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(now)
                    .setExpiration(expiratedDate)
                    .signWith(key)
                    .compact();
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error JWT : " + e.getMessage());
        }
        return false;
    }

    @Override
    public String getId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("id").toString();
    }

}
