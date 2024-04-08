package com.mishacars.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(length = 100)
    private String direccion;

    @Column(length = 50, unique = true)
    private String telefono;

    @Column(name = "correo_electronico", length = 255, unique = true)
    private String correoElectronico;
}