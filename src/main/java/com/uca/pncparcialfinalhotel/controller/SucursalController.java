package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.dto.SucursalRequest;
import com.uca.pncparcialfinalhotel.dto.SucursalResponse;
import com.uca.pncparcialfinalhotel.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public List<SucursalResponse> listar() {
        return sucursalService.listar();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public SucursalResponse crear(@Valid @RequestBody SucursalRequest request) {
        return sucursalService.crear(request);
    }
}