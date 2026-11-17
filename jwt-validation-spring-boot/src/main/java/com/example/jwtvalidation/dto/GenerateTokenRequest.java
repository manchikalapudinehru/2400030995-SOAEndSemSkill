package com.example.jwtvalidation.dto;

import java.util.List;

public class GenerateTokenRequest {
    private String username = "student";
    private List<String> roles = List.of("USER");

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
