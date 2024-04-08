package com.mishacars.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tst.dto.VehiculoDTO;
import tst.dto.DTOEntityMapper;
import tst.entity.Vehiculo;
import tst.service.VehiculoService;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@SecurityRequirement(name = "bearerAuth")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/todos")
    @Operation(summary = "Obtener todos los vehiculos")
    @ApiResponse(responseCode = "200", description = "Lista de vehiculos recuperada exitosamente")
    public ResponseEntity<List<Vehiculo>> getAll() {
        return new ResponseEntity<>(vehiculoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un vehiculo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehiculo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    })
    public ResponseEntity<Vehiculo> getVehiculo(
            @Parameter(name = "id", description = "El ID del vehiculo", example = "1", required = true)
            @PathVariable("id") Integer vehiculoId) {
        return vehiculoService.getVehiculo(vehiculoId)
                .map(vehiculo -> new ResponseEntity<>(vehiculo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/guardar")
    @Operation(summary = "Guardar un nuevo vehiculo")
    @ApiResponse(responseCode = "201", description = "Vehiculo creado")
    public ResponseEntity<Vehiculo> save(
            @Parameter(description = "Vehiculo a ser guardado", required = true)
            @RequestBody VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = DTOEntityMapper.convertToEntity(vehiculoDTO, Vehiculo.class);

        return new ResponseEntity<>(vehiculoService.save(vehiculo), HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un vehiculo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehiculo eliminado"),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado para eliminar")
    })
    public ResponseEntity<Void> delete(
            @Parameter(name = "id", description = "El ID del vehiculo a eliminar", example = "3", required = true)
            @PathVariable("id") Integer vehiculoId) {
        if (vehiculoService.delete(vehiculoId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

