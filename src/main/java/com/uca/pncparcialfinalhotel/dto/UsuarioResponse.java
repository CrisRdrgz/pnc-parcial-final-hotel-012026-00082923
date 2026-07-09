package com.uca.pncparcialfinalhotel.dto;

import com.uca.pncparcialfinalhotel.model.Rol;

public record UsuarioResponse(Long id, String nombreUsuario, String nombreCompleto, Rol rol, Long sucursalId) {}