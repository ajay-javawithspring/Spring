package com.stone.springbootrestblog.security;

import com.stone.springbootrestblog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-millisecond}")
    private String jwtTokenValidityInMs;

    private final Key key = secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication){
        //String username = authentication.getName();

        /*Date currentDate = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(currentDate+ jwtTokenValidityInMs);

        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(expiryDate).signWith(key()).compact();*/
        String username = authentication.getName();

        Instant now = Instant.now();
        Instant expiration = now.plusMillis(Long.parseLong(jwtTokenValidityInMs));

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(key)
                .compact();

        /*String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                //.signWith(SignatureAlgorithm.forSigningKey(secretKeyFor(SignatureAlgorithm.HS512)), jwtSecret)
                .signWith(key)
                .compact();

        return token;*/

    }

    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key)
                    .build()
                    .parse(token);

            return true;
        }
        catch (MalformedJwtException ex) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid Jwt token");
        }
        catch (ExpiredJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "expired Jwt token");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "unsupported Jwt token");
        }
        catch (IllegalArgumentException ex){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Jwt Claims String is empty");
        }
    }






    /*private void validateToken(final JsonWebToken authorizationToken, final PublicKey publicKey,
                               final String correlationId) {
        try {
            Jwts.parser().deserializeJsonWith(JjwtDeserializer.getInstance())
                    .setSigningKey(publicKey)
                    .parse(authorizationToken.getToken());
        } catch (final ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            LOGGER.info("Got Exception '{}' during parsing JWT: {}", e.getClass().getSimpleName(), e.getMessage(),
                    e);
            throw buildJwtUnauthorizedException(correlationId);
        }
    }*/



    /*private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidityInMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // <-- This can be helpful to you
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }*/
}
