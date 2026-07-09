package com.uca.pncparcialfinalhotel.dto;

import com.uca.pncparcialfinalhotel.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequest(
        @NotBlank String nombreUsuario,
        @NotBlank String password,
        @NotBlank String nombreCompleto,
        @NotNull Rol rol,
        Long sucursalId
) {}