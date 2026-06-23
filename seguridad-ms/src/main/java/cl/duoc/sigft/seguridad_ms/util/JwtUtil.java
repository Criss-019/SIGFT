package cl.duoc.sigft.seguridad_ms.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.expirationMs = expirationMs;
    }

    /**
     * Genera un JWT firmado con HMAC-SHA256.
     * Incluye username y rol como claims para que cualquier microservicio
     * pueda validar identidad y permisos sin consultar la BD.
     */
    public String generarToken(String username, String rol) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("rol", rol)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    /** Extrae el username (subject) del token. Lanza JwtException si es inválido. */
    public String extraerUsername(String token) {
        return parsearClaims(token).getSubject();
    }

    /** Extrae el rol del token. */
    public String extraerRol(String token) {
        return parsearClaims(token).get("rol", String.class);
    }

    /** Valida firma y expiración. Retorna false en cualquier error. */
    public boolean esTokenValido(String token) {
        try {
            parsearClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT inválido o expirado: {}", e.getMessage());
            return false;
        }
    }

    private Claims parsearClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
