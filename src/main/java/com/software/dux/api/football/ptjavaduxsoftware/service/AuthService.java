package com.software.dux.api.football.ptjavaduxsoftware.service;

import com.software.dux.api.football.ptjavaduxsoftware.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    public AuthService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> autenticarUsuario(String username, String password) {
        // Verificación estática del usuario
        if ("test".equals(username) && "12345".equals(password)) {
            String token = jwtUtil.generateToken(username);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        } else {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
    }

}
