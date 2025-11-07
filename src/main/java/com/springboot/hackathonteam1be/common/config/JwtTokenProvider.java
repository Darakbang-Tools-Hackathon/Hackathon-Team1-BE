package com.springboot.hackathonteam1be.common.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;
import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JwtTokenProvider {
  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration-ms}")
  private long expirationMs;

  private Key key;
  @PostConstruct
  protected void init(){
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String createToken(String userEmail) {
    Claims claims = Jwts.claims().setSubject(userEmail);
    Date now = new Date();
    Date validity = new Date(now.getTime() + expirationMs);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

public boolean validateToken(String token) {
        try {
            // 토큰을 'key'로 파싱(해석)해 봅니다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            // 성공하면 'true' 반환 (유효함)
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
