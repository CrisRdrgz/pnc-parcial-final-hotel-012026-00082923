package com.uca.pncparcialfinalhotel.dto;

public record LoginResponse(String accessToken, String refreshToken, String rol, String nombreUsuario) {}