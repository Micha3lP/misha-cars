package com.mishacars.service;

import com.mishacars.persistence.entity.Cliente;
import com.mishacars.persistence.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> findById(Integer clienteId) {
        return clienteRepository.findById(clienteId);
    }

    public Iterable<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Integer clienteId, Cliente cliente) {
        return clienteRepository.findById(clienteId)
                .map(existingCliente -> {
                    existingCliente.setNombre(cliente.getNombre());
                    existingCliente.setDireccion(cliente.getDireccion());
                    existingCliente.setTelefono(cliente.getTelefono());
                    existingCliente.setCorreoElectronico(cliente.getCorreoElectronico());
                    // Agrega aquí más campos si es necesario

                    return clienteRepository.save(existingCliente);
                });
    }

    public boolean delete(Integer clienteId) throws Exception {
        if (clienteRepository.existsByClienteIdWithVentas(clienteId)) {
            throw new Exception("El cliente no puede ser eliminado porque tiene compras asociadas.");
        }
        if (clienteRepository.existsById(clienteId)) {
            clienteRepository.deleteById(clienteId);
            return true;
        }
        return false;
    }

    public List<Cliente> findClientesByMarcaVehiculo(String marca) {
        return clienteRepository.findClientesByMarcaVehiculo(marca);
    }

    public List<Cliente> findClientesWithAtLeastOnePurchase() {
        return clienteRepository.findClientesWithAtLeastOnePurchase();
    }

    public Integer getNumeroDeVehiculosDelCliente(Integer clienteId) {
        Integer count = clienteRepository.countVehiculosByClienteId(clienteId);
        return count != null ? count : 0;
    }


}