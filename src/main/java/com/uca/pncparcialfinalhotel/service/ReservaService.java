package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.ReservaRequest;
import com.uca.pncparcialfinalhotel.dto.ReservaResponse;
import com.uca.pncparcialfinalhotel.exception.ResourceNotFoundException;
import com.uca.pncparcialfinalhotel.exception.SucursalMismatchException;
import com.uca.pncparcialfinalhotel.model.*;
import com.uca.pncparcialfinalhotel.repository.ReservaRepository;
import com.uca.pncparcialfinalhotel.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionService habitacionService;
    private final UsuarioService usuarioService;

    public ReservaResponse crear(ReservaRequest request, UserPrincipal principal) {
        Habitacion habitacion = habitacionService.obtenerEntidad(request.habitacionId());
        Usuario usuario = usuarioService.obtenerEntidad(principal.getId());

        Reserva reserva = Reserva.builder()
                .usuario(usuario).habitacion(habitacion)
                .fechaInicio(request.fechaInicio()).fechaFin(request.fechaFin())
                .estado(EstadoReserva.PENDIENTE).build();

        return toResponse(reservaRepository.save(reserva));
    }

    public List<ReservaResponse> misReservas(UserPrincipal principal) {
        return reservaRepository.findByUsuarioId(principal.getId()).stream().map(this::toResponse).toList();
    }

    public List<ReservaResponse> listar(UserPrincipal principal) {
        if ("RECEPCIONISTA".equals(principal.getRol())) {
            return reservaRepository.findByHabitacion_Sucursal_Id(principal.getSucursalId())
                    .stream().map(this::toResponse).toList();
        }
        return reservaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ReservaResponse confirmar(Long id, UserPrincipal principal) {
        Reserva reserva = obtenerEntidad(id);
        validarAccesoSucursal(principal, reserva);
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        return toResponse(reservaRepository.save(reserva));
    }

    public ReservaResponse cancelar(Long id, UserPrincipal principal) {
        Reserva reserva = obtenerEntidad(id);
        validarAccesoSucursal(principal, reserva);
        reserva.setEstado(EstadoReserva.CANCELADA);
        return toResponse(reservaRepository.save(reserva));
    }

    public ReservaResponse modificar(Long id, ReservaRequest request, UserPrincipal principal) {
        Reserva reserva = obtenerEntidad(id);
        validarAccesoSucursal(principal, reserva);
        reserva.setFechaInicio(request.fechaInicio());
        reserva.setFechaFin(request.fechaFin());
        return toResponse(reservaRepository.save(reserva));
    }
    
    private void validarAccesoSucursal(UserPrincipal principal, Reserva reserva) {
        if (!"RECEPCIONISTA".equals(principal.getRol())) return;

        Long sucursalReserva = reserva.getHabitacion().getSucursal().getId();
        if (!sucursalReserva.equals(principal.getSucursalId())) {
            throw new SucursalMismatchException("No puede operar sobre reservas de una sucursal distinta a la suya");
        }
    }

    private Reserva obtenerEntidad(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada: " + id));
    }

    private ReservaResponse toResponse(Reserva r) {
        return new ReservaResponse(r.getId(), r.getHabitacion().getId(), r.getHabitacion().getNumero(),
                r.getHabitacion().getSucursal().getId(), r.getUsuario().getId(),
                r.getFechaInicio(), r.getFechaFin(), r.getEstado());
    }
}