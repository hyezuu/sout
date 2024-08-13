package com.sout.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-seconds}")
    private int accessTokenExpirationSeconds;

    @Value("${jwt.refresh-token-expiration-seconds}")
    private int refreshTokenExpirationSeconds;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken(Map<String, Object> claims,
        String subject, int expirationSeconds, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationSeconds * 1000L);

        JwtBuilder builder = Jwts.builder()
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(key);

        if (claims != null) {
            builder.setClaims(claims);
        }
        if (subject != null) {
            builder.setSubject(subject);
        }

        return builder.compact();
    }

    public String generateAccessToken(Map<String, Object> claims, String subject,
        String base64EncodedSecretKey) {
        return generateToken(claims, subject, accessTokenExpirationSeconds, base64EncodedSecretKey);
    }

    public String generateRefreshToken(String subject, String base64EncodedSecretKey) {
        return generateToken(null, subject, refreshTokenExpirationSeconds, base64EncodedSecretKey);
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Jws<Claims> parseToken(String token, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token, encodeBase64SecretKey(secretKey));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public String getSubjectFromRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claims = parseToken(refreshToken, encodeBase64SecretKey(secretKey));
            return claims.getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            return null;
        }
    }

    public Claims getClaims(String token) {
        try {
            return parseToken(token, encodeBase64SecretKey(secretKey)).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Failed to get claims from token: {}", e.getMessage());
            return null;
        }
    }
}

