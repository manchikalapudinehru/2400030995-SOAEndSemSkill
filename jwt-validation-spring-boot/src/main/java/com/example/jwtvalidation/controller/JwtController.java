package com.example.jwtvalidation.controller;

import com.example.jwtvalidation.dto.*;
import com.example.jwtvalidation.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenResponse> validate(@RequestBody TokenRequest request) {
        try {
            if (request == null || request.getToken() == null || request.getToken().isBlank()) {
                throw new IllegalArgumentException("Token is required");
            }

            Claims claims = jwtService.validateToken(request.getToken());
            Object rolesClaim = claims.get("roles");
            List<String> roles = rolesClaim instanceof List<?> values
                    ? values.stream().map(String::valueOf).toList()
                    : Collections.emptyList();

            return ResponseEntity.ok(new TokenResponse(
                    true, "Token Valid", claims.getSubject(), roles));
        } catch (JwtException | IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponse(false, "Invalid Token", null, Collections.emptyList()));
        }
    }

    @PostMapping("/generate")
    public GeneratedTokenResponse generate(@RequestBody(required = false) GenerateTokenRequest request) {
        GenerateTokenRequest data = request == null ? new GenerateTokenRequest() : request;
        return new GeneratedTokenResponse(
                jwtService.generateToken(data.getUsername(), data.getRoles()));
    }
}
