package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.LoginRequest;
import com.uca.pncparcialfinalhotel.dto.LoginResponse;
import com.uca.pncparcialfinalhotel.security.JwtService;
import com.uca.pncparcialfinalhotel.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.nombreUsuario(), request.password())
        );
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return new LoginResponse(
                jwtService.generateAccessToken(principal), jwtService.generateRefreshToken(principal),
                principal.getRol(), principal.getUsername()
        );
    }

    public LoginResponse refresh(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken, "refresh")) {
            throw new IllegalArgumentException("Refresh token inválido o expirado");
        }
        Claims claims = jwtService.extractClaims(refreshToken);
        UserPrincipal principal = new UserPrincipal(
                claims.get("userId", Long.class), claims.getSubject(), "",
                claims.get("rol", String.class), claims.get("sucursalId", Long.class)
        );

        return new LoginResponse(jwtService.generateAccessToken(principal), refreshToken, principal.getRol(), principal.getUsername());
    }
}