package com.example.SpringJWT.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    public String signinKey="67566B59703373367639792442264529482B4D6251655468576D5A7134743777";
    public String ExtractUsername(String token) {

        return ExtractClaim(token,Claims::getSubject);
    }
    public <T> T ExtractClaim(String token, Function<Claims,T>ClaimResolver)
    {
        Claims claims=ExtractAllClaims(token);
        return ClaimResolver.apply(claims);
    }
    public  String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<>(),userDetails);
    }
    
    public boolean isValidToken(UserDetails userDetails,String Token)
    {
        String username=ExtractUsername(Token);
        return username.equals(userDetails.getUsername())&&!NotExpired(Token);
    }

    private boolean NotExpired(String token) {
        return  expireToken(token).before(new Date());
    }

    private Date expireToken(String token) {
        return ExtractClaim(token,Claims::getExpiration);
    }

    public String generateToken(Map<String, Objects> Claim, UserDetails userDetails)
    {
        return Jwts.builder()
                .setClaims(Claim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000*60))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims ExtractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keys= Decoders.BASE64.decode(signinKey);
        return Keys.hmacShaKeyFor(keys);
    }
}
