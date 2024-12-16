package com.software.dux.api.football.ptjavaduxsoftware.controller;

import com.software.dux.api.football.ptjavaduxsoftware.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try{
            String username = request.get("username");
            String password = request.get("password");
            Map<String, String> response = authService.autenticarUsuario(username, password);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(401).body(Map.of(
                    "mensaje", "Autenticación fallida"
            ));
        }
    }

}
