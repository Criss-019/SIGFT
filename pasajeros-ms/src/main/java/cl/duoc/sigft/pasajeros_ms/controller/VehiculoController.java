package cl.duoc.sigft.pasajeros_ms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.sigft.pasajeros_ms.dto.VehiculoDTO;
import cl.duoc.sigft.pasajeros_ms.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
    private final VehiculoService service;

    @PostMapping
    public ResponseEntity<VehiculoDTO> registrar(@Valid @RequestBody VehiculoDTO dto) {
        log.info("Capa Controller - Petición POST para registrar vehículo");
        return new ResponseEntity<>(service.registrar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> obtenerPorPatente(@PathVariable String patente) {
        log.info("Capa Controller - Petición GET para patente: {}", patente);
        return ResponseEntity.ok(service.obtenerPorPatente(patente));
    }

    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> obtenerTodos() {
        log.info("Capa Controller - Petición GET para listar vehículos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/propietario/{rut}")
    public ResponseEntity<List<VehiculoDTO>> obtenerPorPropietario(@PathVariable String rut) {
        log.info("Capa Controller - Petición GET para vehículos del RUT: {}", rut);
        return ResponseEntity.ok(service.obtenerPorRutPropietario(rut));
    }

    @PutMapping("/{patente}")
    public ResponseEntity<VehiculoDTO> actualizar(@PathVariable String patente, @Valid @RequestBody VehiculoDTO dto) {
        log.info("Capa Controller - Petición PUT para actualizar patente: {}", patente);
        return ResponseEntity.ok(service.actualizar(patente, dto));
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        log.info("Capa Controller - Petición DELETE para patente: {}", patente);
        service.eliminar(patente);
        return ResponseEntity.noContent().build();
    }

}
