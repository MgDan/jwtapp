package org.dandroid.jwtapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
    private static Key securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); //token?
    private long validityInMilliseconds = 3600000; // 1 hora

   // @PostConstruct
   // public void init(){this.securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);}

    public String generateToken(String username, String password) {

        // payload, signature, header
        Map<String, Object> claims = new HashMap<>();
        claims.put("password", password);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(securityKey)
                .compact();
    }

    public static boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public static String getPasswordFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("password", String.class);
    }
}
