package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);
    List<Reserva> findByHabitacion_Sucursal_Id(Long sucursalId);
}