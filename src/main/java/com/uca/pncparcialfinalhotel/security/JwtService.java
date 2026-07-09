package com.uca.pncparcialfinalhotel.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessExpirationMs;
    private final long refreshExpirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-expiration-ms}") long accessExpirationMs,
            @Value("${jwt.refresh-expiration-ms}") long refreshExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessExpirationMs = accessExpirationMs;
        this.refreshExpirationMs = refreshExpirationMs;
    }

    public String generateAccessToken(UserPrincipal principal) {
        return buildToken(principal, accessExpirationMs, "access");
    }

    public String generateRefreshToken(UserPrincipal principal) {
        return buildToken(principal, refreshExpirationMs, "refresh");
    }

    private String buildToken(UserPrincipal principal, long expirationMs, String type) {
        Date now = new Date();
        return Jwts.builder()
                .subject(principal.getUsername())
                .claim("userId", principal.getId())
                .claim("rol", principal.getRol())
                .claim("sucursalId", principal.getSucursalId())
                .claim("type", type)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, String expectedType) {
        try {
            Claims claims = extractClaims(token);
            return expectedType.equals(claims.get("type", String.class)) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}