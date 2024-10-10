package com.micro.auth_server.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtHelper {

    @Value("")                                                                                                          //De springFramework
    private String jwtSecret;

    public String createToken(String username) {
        final var now = new Date();
        final var expirationDate = new Date(now.getTime() + 1000 * 3600);
        return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate).signWith(this.getSecretKey()).compact();
    }

    public boolean validateToken(String token) {
        final var expirationDate = this.getExpirationDate(token);
        return expirationDate.after(new Date());
    }

    private Date getExpirationDate(String token){
        return this.getClaimsForToken(token, Claims::getExpiration);
    }
    private <T> T getClaimsForToken(String token, Function<Claims,T> resolver) {                                        //Function de java.util
        return resolver.apply(this.signToken(token));
    }

    private Claims signToken(String token) {
        return Jwts.parserBuilder().setSigningKey(this.getSecretKey())
                .build().parseClaimsJws(token).getBody();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
    };
}
