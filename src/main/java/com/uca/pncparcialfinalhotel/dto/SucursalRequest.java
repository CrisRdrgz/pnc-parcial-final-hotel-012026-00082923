package com.uca.pncparcialfinalhotel.dto;

import jakarta.validation.constraints.NotBlank;

public record SucursalRequest(@NotBlank String nombre, @NotBlank String ciudad, String direccion) {}