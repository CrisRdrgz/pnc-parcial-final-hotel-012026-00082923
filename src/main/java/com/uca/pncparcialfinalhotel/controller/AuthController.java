package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.dto.LoginRequest;
import com.uca.pncparcialfinalhotel.dto.LoginResponse;
import com.uca.pncparcialfinalhotel.dto.RefreshRequest;
import com.uca.pncparcialfinalhotel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return authService.refresh(request.refreshToken());
    }
}