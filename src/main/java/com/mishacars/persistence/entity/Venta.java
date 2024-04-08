package com.mishacars.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private Integer ventaId;

    @Column(name = "vehiculo_id")
    private Integer vehiculoId;

    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "fecha_venta")
    private Date fechaVenta;

    @Column(name = "precio_venta", precision = 15, scale = 2)
    private BigDecimal precioVenta;

    // Claves foráneas (opcional, depende de cómo quieras manejar las relaciones en JPA)
    @ManyToOne
    @JoinColumn(name = "vehiculo_id", insertable = false, updatable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    private Cliente cliente;
}