package com.mishacars.web.controller;

import com.mishacars.persistence.entity.Cliente;
import com.mishacars.persistence.entity.Vehiculo;
import com.mishacars.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return ResponseEntity.ok((List<Cliente>) clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        return clienteService.update(id, cliente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // HttpStatus.CONFLICT es solo una sugerencia; elige el código de estado que mejor se ajuste a tu caso
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Cliente>> getClientesByMarcaVehiculo(@PathVariable String marca) {
        List<Cliente> clientes = clienteService.findClientesByMarcaVehiculo(marca);
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/minimo")
    public ResponseEntity<List<Cliente>> findClientesWithAtLeastOnePurchase() {
        List<Cliente> clientes = clienteService.findClientesWithAtLeastOnePurchase();
        if (clientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/vehiculos/{clienteId}")
    public ResponseEntity<Integer> getNumeroDeVehiculosDelCliente(@PathVariable Integer clienteId) {
        boolean clienteExists = clienteService.findById(clienteId).isPresent();

        if (clienteExists) {
            Integer numeroDeVehiculos = clienteService.getNumeroDeVehiculosDelCliente(clienteId);
            return ResponseEntity.ok(numeroDeVehiculos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}