package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.Entities.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface JWTService {

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    public boolean isTokenValid(String token, UserDetails userDetails);
    public Long getExpirationTime();
    public String extractUsername(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    public Map<String, Object> getTokenClaims(User user, List<String> fields);


}
