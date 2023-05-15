package com.elibrary.group4.Utils.Validation;

import com.elibrary.group4.Utils.Constants.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt_secret}")
    private String jwtSecret;
    @Value("${jwt_expire}")
    private Integer jwtExparation;


    public String generateToken(String userId, Role role) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(role.toString())
                .setId(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExparation))
                .signWith(SignatureAlgorithm.HS256, jwtSecret);
        return builder.compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            throw new RuntimeException("Invalid Jwt Token");
        }catch (ExpiredJwtException e){
            throw new RuntimeException("Token is expired");
        }catch (UnsupportedJwtException e){
            throw new RuntimeException("Jwt Token Unsupported");
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Jwt is invalid");
        }catch (SignatureException e){
            throw new RuntimeException("Jwt Signature not match");
        }
    }
}
