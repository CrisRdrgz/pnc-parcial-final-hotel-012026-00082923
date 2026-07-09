package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.dto.ReservaRequest;
import com.uca.pncparcialfinalhotel.dto.ReservaResponse;
import com.uca.pncparcialfinalhotel.security.UserPrincipal;
import com.uca.pncparcialfinalhotel.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @PreAuthorize("hasRole('HUESPED')")
    public ReservaResponse crear(@Valid @RequestBody ReservaRequest request, @AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.crear(request, principal);
    }

    @GetMapping("/mias")
    @PreAuthorize("hasRole('HUESPED')")
    public List<ReservaResponse> misReservas(@AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.misReservas(principal);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECEPCIONISTA')")
    public List<ReservaResponse> listar(@AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.listar(principal);
    }

    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECEPCIONISTA')")
    public ReservaResponse confirmar(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.confirmar(id, principal);
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECEPCIONISTA')")
    public ReservaResponse cancelar(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.cancelar(id, principal);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','RECEPCIONISTA')")
    public ReservaResponse modificar(@PathVariable Long id, @Valid @RequestBody ReservaRequest request, @AuthenticationPrincipal UserPrincipal principal) {
        return reservaService.modificar(id, request, principal);
    }
}