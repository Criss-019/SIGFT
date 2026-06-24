package cl.duoc.sigft.tramites_ms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.sigft.tramites_ms.dto.DeclaracionJuradaSAGDTO;
import cl.duoc.sigft.tramites_ms.service.DeclaracionJuradaSAGService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/declaraciones-sag")
@RequiredArgsConstructor
public class DeclaracionJuradaSAGController {
    // Se inyecta la interfaz, manteniendo el acoplamiento débil
    private final DeclaracionJuradaSAGService service;

    @PostMapping
    public ResponseEntity<DeclaracionJuradaSAGDTO> registrar(@Valid @RequestBody DeclaracionJuradaSAGDTO dto) {
        log.info("REST Request - POST /api/v1/declaraciones-sag");
        DeclaracionJuradaSAGDTO response = service.registrar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeclaracionJuradaSAGDTO> obtenerPorId(@PathVariable("id") String idDeclaracion) {
        log.info("REST Request - GET /api/v1/declaraciones-sag/{}", idDeclaracion);
        DeclaracionJuradaSAGDTO response = service.obtenerPorId(idDeclaracion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pasajero/{rut}")
    public ResponseEntity<List<DeclaracionJuradaSAGDTO>> obtenerPorRutPasajero(@PathVariable("rut") String rutPasajero) {
        log.info("REST Request - GET /api/v1/declaraciones-sag/pasajero/{}", rutPasajero);
        List<DeclaracionJuradaSAGDTO> response = service.obtenerPorRutPasajero(rutPasajero);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DeclaracionJuradaSAGDTO>> obtenerTodas() {
        log.info("REST Request - GET /api/v1/declaraciones-sag");
        List<DeclaracionJuradaSAGDTO> response = service.obtenerTodas();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") String idDeclaracion) {
        log.info("REST Request - DELETE /api/v1/declaraciones-sag/{}", idDeclaracion);
        service.eliminar(idDeclaracion);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

}
