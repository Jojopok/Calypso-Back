package org.calypso.calypso.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.calypso.calypso.model.auth.User;
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
     * Générer un token JWT avec uniquement l'ID utilisateur
     */
    public String generateToken(Long userId, String email, Set<Long> roleIds) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))  // ID utilisateur dans le `sub`
                .claim("email", email)
                .claim("roleIds", roleIds)

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public String extractEmailFromToken(String token) {
        // Ici tu extraies l'email à partir du token JWT, avec la bibliothèque JWT que tu utilises
        // Exemple avec 'io.jsonwebtoken.Jwts' et 'secretKey'

        try {
            String jwtToken = token.substring(7); // Retirer "Bearer " du token
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(jwtToken)
                    .getBody();

            return claims.get("email", String.class); // Extraire l'email du token
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'extraction de l'email depuis le token", e);
        }
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
