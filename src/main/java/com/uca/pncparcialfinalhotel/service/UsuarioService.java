package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.UsuarioRequest;
import com.uca.pncparcialfinalhotel.dto.UsuarioResponse;
import com.uca.pncparcialfinalhotel.exception.ResourceNotFoundException;
import com.uca.pncparcialfinalhotel.model.Rol;
import com.uca.pncparcialfinalhotel.model.Sucursal;
import com.uca.pncparcialfinalhotel.model.Usuario;
import com.uca.pncparcialfinalhotel.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SucursalService sucursalService;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UsuarioResponse crear(UsuarioRequest request) {
        Sucursal sucursal = null;
        if (request.rol() == Rol.RECEPCIONISTA) {
            if (request.sucursalId() == null) {
                throw new IllegalArgumentException("Un recepcionista debe tener una sucursal asignada");
            }
            sucursal = sucursalService.obtenerEntidad(request.sucursalId());
        }

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.nombreUsuario())
                .password(passwordEncoder.encode(request.password()))
                .nombreCompleto(request.nombreCompleto())
                .rol(request.rol())
                .sucursal(sucursal)
                .build();

        return toResponse(usuarioRepository.save(usuario));
    }

    public Usuario obtenerEntidad(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
    }

    private UsuarioResponse toResponse(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombreUsuario(), u.getNombreCompleto(), u.getRol(),
                u.getSucursal() != null ? u.getSucursal().getId() : null);
    }
}