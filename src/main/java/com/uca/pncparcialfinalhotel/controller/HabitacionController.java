package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.dto.HabitacionRequest;
import com.uca.pncparcialfinalhotel.dto.HabitacionResponse;
import com.uca.pncparcialfinalhotel.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public List<HabitacionResponse> listar() {
        return habitacionService.listar();
    }

    @GetMapping("/sucursal/{sucursalId}")
    public List<HabitacionResponse> listarPorSucursal(@PathVariable Long sucursalId) {
        return habitacionService.listarPorSucursal(sucursalId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public HabitacionResponse crear(@Valid @RequestBody HabitacionRequest request) {
        return habitacionService.crear(request);
    }
}