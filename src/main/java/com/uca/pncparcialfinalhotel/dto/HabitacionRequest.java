package com.uca.pncparcialfinalhotel.dto;

import com.uca.pncparcialfinalhotel.model.TipoHabitacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record HabitacionRequest(
        @NotBlank String numero,
        @NotNull TipoHabitacion tipo,
        @NotNull BigDecimal precio,
        boolean disponible,
        @NotNull Long sucursalId
) {}