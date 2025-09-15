package com.example.pedidos.infrastructure.adapters;

// import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.pedidos.domain.model.User;
import com.example.pedidos.domain.ports.TokenProviderPort;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProviderAdapter implements TokenProviderPort{
    
    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration:86400000}")
    private long expiration;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generarToken(User usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration); // expiration en ms

        // Debug: imprimir fechas completas
        System.out.println("FECHA ACTUAL: " + now);            // fecha y hora actuales
        System.out.println("FECHA EXPIRACION: " + expiryDate);

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("rol", usuario.getRol())
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("EOOR");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String obtenerUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @Override
    public Claims obtenerClaims(String token) {
        return parseClaims(token);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSecretKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
