package com.uca.pncparcialfinalhotel.dto;

import com.uca.pncparcialfinalhotel.model.EstadoReserva;
import java.time.LocalDate;

public record ReservaResponse(
        Long id, Long habitacionId, String numeroHabitacion, Long sucursalId,
        Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin, EstadoReserva estado
) {}