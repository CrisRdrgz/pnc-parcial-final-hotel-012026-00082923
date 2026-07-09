package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.HabitacionRequest;
import com.uca.pncparcialfinalhotel.dto.HabitacionResponse;
import com.uca.pncparcialfinalhotel.exception.ResourceNotFoundException;
import com.uca.pncparcialfinalhotel.model.Habitacion;
import com.uca.pncparcialfinalhotel.model.Sucursal;
import com.uca.pncparcialfinalhotel.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;
    private final SucursalService sucursalService;

    public List<HabitacionResponse> listar() {
        return habitacionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<HabitacionResponse> listarPorSucursal(Long sucursalId) {
        return habitacionRepository.findBySucursalId(sucursalId).stream().map(this::toResponse).toList();
    }

    public HabitacionResponse crear(HabitacionRequest request) {
        Sucursal sucursal = sucursalService.obtenerEntidad(request.sucursalId());
        Habitacion habitacion = Habitacion.builder()
                .numero(request.numero()).tipo(request.tipo()).precio(request.precio())
                .disponible(request.disponible()).sucursal(sucursal).build();
        return toResponse(habitacionRepository.save(habitacion));
    }

    public Habitacion obtenerEntidad(Long id) {
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada: " + id));
    }

    private HabitacionResponse toResponse(Habitacion h) {
        return new HabitacionResponse(h.getId(), h.getNumero(), h.getTipo(), h.getPrecio(),
                h.isDisponible(), h.getSucursal().getId(), h.getSucursal().getNombre());
    }
}