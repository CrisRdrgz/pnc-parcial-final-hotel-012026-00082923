package com.uca.pncparcialfinalhotel.dto;

import com.uca.pncparcialfinalhotel.model.TipoHabitacion;
import java.math.BigDecimal;

public record HabitacionResponse(
        Long id, String numero, TipoHabitacion tipo, BigDecimal precio,
        boolean disponible, Long sucursalId, String sucursalNombre
) {}