package com.mishacars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tst.entity.Vehiculo;
import tst.repository.VehiculoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> getVehiculo(Integer vehiculoId) {
        return vehiculoRepository.findById(vehiculoId);
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public boolean delete(Integer vehiculoId) {
        return getVehiculo(vehiculoId).map(vehiculo -> {
            vehiculoRepository.delete(vehiculo);
            return true;
        }).orElse(false);
    }
}
