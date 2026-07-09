package com.uca.pncparcialfinalhotel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sucursales")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    private String direccion;
}