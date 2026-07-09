package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.model.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findBySucursalId(Long sucursalId);
}