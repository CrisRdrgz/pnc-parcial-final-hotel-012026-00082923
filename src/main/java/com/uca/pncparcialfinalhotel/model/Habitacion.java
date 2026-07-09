package com.uca.pncparcialfinalhotel.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "habitaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipo;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private boolean disponible;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
}