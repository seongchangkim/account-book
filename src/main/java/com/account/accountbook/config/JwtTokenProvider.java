package com.account.accountbook.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    public static String header = "Authorization";
    @Value("${app.jwt-secret-key}")
    private String secretKey;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void secretKeyEncoding(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String registerToken(Long userId){
        Claims claims = Jwts.claims().setSubject(userId.toString());
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuedAt(now).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Authentication checkAuth(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserInfo(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }
}
