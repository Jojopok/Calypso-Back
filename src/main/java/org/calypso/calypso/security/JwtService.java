package org.calypso.calypso.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Générer un token JWT avec les rôles depuis UserDetails
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())  // Utilisation sécurisée
                .compact();
    }

    /**
     * Générer un token JWT avec uniquement l'ID utilisateur
     */
    public String generateTokenWithUserId(Long userId, String email, Set<Long> roleIds) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))  // ID utilisateur dans le `sub`
                .claim("email", email)               // Ajout de l'email
                .claim("roleIds", roleIds)           // Ajout des IDs de rôles dans le token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())  // Clé sécurisée
                .compact();
    }


    /**
     * Extraire les claims depuis le token
     */
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Valider le token JWT
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
