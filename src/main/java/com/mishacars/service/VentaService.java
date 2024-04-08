package com.mishacars.service;

import com.mishacars.persistence.entity.Venta;
import com.mishacars.persistence.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    @Autowired
    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public Iterable<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> findById(Integer id) {
        return ventaRepository.findById(id);
    }

    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Optional<Venta> update(Integer id, Venta updatedVenta) {
        return ventaRepository.findById(id)
                .map(venta -> {
                    venta.setVehiculoId(updatedVenta.getVehiculoId());
                    venta.setClienteId(updatedVenta.getClienteId());
                    venta.setFechaVenta(updatedVenta.getFechaVenta());
                    venta.setPrecioVenta(updatedVenta.getPrecioVenta());

                    if (updatedVenta.getVehiculo() != null) {
                        venta.setVehiculo(updatedVenta.getVehiculo());
                    }
                    if (updatedVenta.getCliente() != null) {
                        venta.setCliente(updatedVenta.getCliente());
                    }

                    return ventaRepository.save(venta);
                });
    }


    public boolean existsById(Integer id) {
        return ventaRepository.existsById(id);
    }

    public void deleteById(Integer id) {
        ventaRepository.deleteById(id);
    }


}