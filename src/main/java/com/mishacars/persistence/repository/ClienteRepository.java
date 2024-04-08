package com.mishacars.persistence.repository;

import com.mishacars.persistence.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Integer> {

    @Query(value = "SELECT DISTINCT c.cliente_id, c.nombre, c.direccion, c.telefono, c.correo_electronico " +
            "FROM clientes c " +
            "JOIN ventas v ON c.cliente_id = v.cliente_id " +
            "JOIN vehiculo ve ON v.vehiculo_id = ve.id " +
            "WHERE ve.marca = :marca", nativeQuery = true)
    List<Cliente> findClientesByMarcaVehiculo(@Param("marca") String marca);

    @Query(value = "SELECT DISTINCT " +
            "c.cliente_id, c.nombre, c.direccion, c.telefono, c.correo_electronico " +
            "FROM clientes c " +
            "JOIN ventas v ON c.cliente_id = v.cliente_id " +
            "JOIN vehiculo ve ON v.vehiculo_id = ve.id " +
            "GROUP BY c.cliente_id " +
            "HAVING COUNT(v.venta_id) >= 1", nativeQuery = true)
    List<Cliente> findClientesWithAtLeastOnePurchase();

    @Query(value = "SELECT COUNT(v.vehiculo_id) " +
            "FROM clientes c " +
            "JOIN ventas v ON c.cliente_id = v.cliente_id " +
            "WHERE c.cliente_id = :clienteId " +
            "GROUP BY c.cliente_id", nativeQuery = true)
    Integer countVehiculosByClienteId(@Param("clienteId") Integer clienteId);

    @Query(value = "SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM ventas v WHERE v.cliente_id = :clienteId", nativeQuery = true)
    boolean existsByClienteIdWithVentas(@Param("clienteId") Integer clienteId);


}