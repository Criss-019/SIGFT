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

import cl.duoc.sigft.pasajeros_ms.dto.PasajeroDTO;
import cl.duoc.sigft.pasajeros_ms.service.PasajeroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pasajeros")
@RequiredArgsConstructor
public class PasajeroController {
    private final PasajeroService service;

    @PostMapping
    public ResponseEntity<PasajeroDTO> crear(@Valid @RequestBody PasajeroDTO dto) {
        log.info("Capa Controller - Petición POST recibida para crear pasajero");
        return new ResponseEntity<>(service.registrar(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<PasajeroDTO> obtenerPorRut(@PathVariable String rut) {
        log.info("Capa Controller - Petición GET recibida para RUT: {}", rut);
        return ResponseEntity.ok(service.obtenerPorRut(rut));
    }

    @GetMapping
    public ResponseEntity<List<PasajeroDTO>> obtenerTodos() {
        log.info("Capa Controller - Petición GET recibida para obtener todos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @PutMapping("/{rut}")
    public ResponseEntity<PasajeroDTO> actualizar(@PathVariable String rut, @Valid @RequestBody PasajeroDTO dto) {
        log.info("Capa Controller - Petición PUT recibida para RUT: {}", rut);
        return ResponseEntity.ok(service.actualizar(rut, dto));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable String rut) {
        log.info("Capa Controller - Petición DELETE recibida para RUT: {}", rut);
        service.eliminar(rut);
        return ResponseEntity.noContent().build();
    }

}
