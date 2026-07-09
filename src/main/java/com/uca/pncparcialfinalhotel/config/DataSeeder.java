package com.uca.pncparcialfinalhotel.config;

import com.uca.pncparcialfinalhotel.model.*;
import com.uca.pncparcialfinalhotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final SucursalRepository sucursalRepository;
    private final HabitacionRepository habitacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (sucursalRepository.count() > 0) return;

        Sucursal central = sucursalRepository.save(
                Sucursal.builder().nombre("Sucursal Central").ciudad("San Salvador").direccion("Col. Escalón").build());
        Sucursal norte = sucursalRepository.save(
                Sucursal.builder().nombre("Sucursal Norte").ciudad("Santa Ana").direccion("Centro").build());

        habitacionRepository.save(Habitacion.builder().numero("101").tipo(TipoHabitacion.SIMPLE)
                .precio(new BigDecimal("45.00")).disponible(true).sucursal(central).build());
        habitacionRepository.save(Habitacion.builder().numero("201").tipo(TipoHabitacion.DOBLE)
                .precio(new BigDecimal("65.00")).disponible(true).sucursal(norte).build());

        usuarioRepository.save(Usuario.builder().nombreUsuario("admin").password(passwordEncoder.encode("admin123"))
                .nombreCompleto("Administrador General").rol(Rol.ADMINISTRADOR).build());

        usuarioRepository.save(Usuario.builder().nombreUsuario("recep.central").password(passwordEncoder.encode("recep123"))
                .nombreCompleto("Recepcionista Central").rol(Rol.RECEPCIONISTA).sucursal(central).build());

        usuarioRepository.save(Usuario.builder().nombreUsuario("recep.norte").password(passwordEncoder.encode("recep123"))
                .nombreCompleto("Recepcionista Norte").rol(Rol.RECEPCIONISTA).sucursal(norte).build());

        usuarioRepository.save(Usuario.builder().nombreUsuario("huesped1").password(passwordEncoder.encode("huesped123"))
                .nombreCompleto("Cliente de Prueba").rol(Rol.HUESPED).build());
    }
}