package com.example.TechnoShark.SchoolRanking.Auth.Service;

import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.TechnoShark.SchoolRanking.Auth.DTO.JwtUserResponse;
import com.example.TechnoShark.SchoolRanking.Auth.Model.CustomUserDetails;
import com.example.TechnoShark.SchoolRanking.Config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtProperties jwtProperties;
    // private final RedisTemplate<String, String> redisTemplate;

    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(CustomUserDetails userDetails) {
        return generateToken(userDetails, jwtProperties.getAccessTokenExpiration());
    }

    public String generateRefreshToken(CustomUserDetails userDetails) {
        String refreshToken = generateToken(userDetails, jwtProperties.getRefreshTokenExpiration());
        // Store refresh token in Redis with expiration
        // String redisKey = "refresh_token:" + userDetails.getUsername();
        // redisTemplate.opsForValue().set(redisKey, refreshToken,
        // Duration.ofMillis(jwtProperties.getRefreshTokenExpiration()));
        return refreshToken;
    }

    private String generateToken(CustomUserDetails userDetails, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        JwtUserResponse jwtUser = userDetails.getUser();

        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .add("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .add("user", jwtUser)
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {

            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateRefreshToken(String refreshToken, String username) {
        return true;
        // try {
        // String redisKey = "refresh_token:" + username;
        // String storedToken = redisTemplate.opsForValue().get(redisKey);

        // if (storedToken == null || !storedToken.equals(refreshToken)) {
        // return false;
        // }

        // return !isTokenExpired(refreshToken);
        // } catch (Exception e) {
        // log.error("Error validating refresh token: {}", e.getMessage());
        // return false;
        // }
    }

    public void revokeRefreshToken(String username) {
        // String redisKey = "refresh_token:" + username;
        // redisTemplate.delete(redisKey);
    }
}