package com.mishacars.persistence.repository;

import com.mishacars.persistence.entity.Venta;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface VentaRepository extends CrudRepository<Venta, Integer> {
    boolean existsByVehiculoId(Integer vehiculoId);


}