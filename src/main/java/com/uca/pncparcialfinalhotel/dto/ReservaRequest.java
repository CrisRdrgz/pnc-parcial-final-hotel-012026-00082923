package com.uca.pncparcialfinalhotel.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservaRequest(@NotNull Long habitacionId, @NotNull LocalDate fechaInicio, @NotNull LocalDate fechaFin) {}