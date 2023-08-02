package com.example.JWT.jwt;

import com.example.JWT.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION=24*60*60*1000;
    @Value("${app.jwt.secret}")

    private String secretKey;

    public String generateAccessToken(User user){

        return Jwts.builder()
                .setSubject(user.getName()+","+user.getEmail())
                .setIssuer("JWT")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();

    }
}
