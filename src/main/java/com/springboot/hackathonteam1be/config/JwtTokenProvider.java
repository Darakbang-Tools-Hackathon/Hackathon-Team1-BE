package com.springboot.hackathonteam1be.config;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import javax.annotation.PostConstruct;

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
}
