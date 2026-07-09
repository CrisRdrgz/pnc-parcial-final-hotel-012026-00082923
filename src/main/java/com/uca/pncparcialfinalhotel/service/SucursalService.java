package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.SucursalRequest;
import com.uca.pncparcialfinalhotel.dto.SucursalResponse;
import com.uca.pncparcialfinalhotel.exception.ResourceNotFoundException;
import com.uca.pncparcialfinalhotel.model.Sucursal;
import com.uca.pncparcialfinalhotel.repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    public List<SucursalResponse> listar() {
        return sucursalRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SucursalResponse crear(SucursalRequest request) {
        Sucursal sucursal = Sucursal.builder()
                .nombre(request.nombre()).ciudad(request.ciudad()).direccion(request.direccion()).build();
        return toResponse(sucursalRepository.save(sucursal));
    }

    public Sucursal obtenerEntidad(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada: " + id));
    }

    private SucursalResponse toResponse(Sucursal s) {
        return new SucursalResponse(s.getId(), s.getNombre(), s.getCiudad(), s.getDireccion());
    }
}