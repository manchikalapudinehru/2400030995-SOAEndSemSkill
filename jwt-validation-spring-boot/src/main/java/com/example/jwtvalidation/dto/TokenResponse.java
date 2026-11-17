package com.example.jwtvalidation.dto;

import java.util.List;

public class TokenResponse {
    private final boolean valid;
    private final String message;
    private final String username;
    private final List<String> roles;

    public TokenResponse(boolean valid, String message, String username, List<String> roles) {
        this.valid = valid;
        this.message = message;
        this.username = username;
        this.roles = roles;
    }

    public boolean isValid() { return valid; }
    public String getMessage() { return message; }
    public String getUsername() { return username; }
    public List<String> getRoles() { return roles; }
}
