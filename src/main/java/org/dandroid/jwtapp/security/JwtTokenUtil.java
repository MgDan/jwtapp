package org.dandroid.jwtapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Named
@ApplicationScoped
public class JwtTokenUtil {
    private Key securityKey; //token?
    private long validityInMilliseconds = 3600000; // 1 hora

    @PostConstruct
    public void init(){
        this.securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(securityKey)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
