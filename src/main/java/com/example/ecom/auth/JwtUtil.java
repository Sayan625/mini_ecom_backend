package com.example.ecom.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET = "mysecretkeymysecretkeymysecretkey";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
            return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class);
    }

}