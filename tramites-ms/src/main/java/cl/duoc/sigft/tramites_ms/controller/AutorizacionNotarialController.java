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

import cl.duoc.sigft.tramites_ms.dto.AutorizacionNotarialDTO;
import cl.duoc.sigft.tramites_ms.service.AutorizacionNotarialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/autorizaciones-notariales")
@RequiredArgsConstructor
public class AutorizacionNotarialController {
    // Se inyecta la interfaz
    private final AutorizacionNotarialService service;

    @PostMapping
    public ResponseEntity<AutorizacionNotarialDTO> registrar(@Valid @RequestBody AutorizacionNotarialDTO dto) {
        log.info("REST Request - POST /api/v1/autorizaciones-notariales");
        AutorizacionNotarialDTO response = service.registrar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorizacionNotarialDTO> obtenerPorId(@PathVariable("id") String idAutorizacion) {
        log.info("REST Request - GET /api/v1/autorizaciones-notariales/{}", idAutorizacion);
        AutorizacionNotarialDTO response = service.obtenerPorId(idAutorizacion);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AutorizacionNotarialDTO>> obtenerTodas() {
        log.info("REST Request - GET /api/v1/autorizaciones-notariales");
        List<AutorizacionNotarialDTO> response = service.obtenerTodas();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") String idAutorizacion) {
        log.info("REST Request - DELETE /api/v1/autorizaciones-notariales/{}", idAutorizacion);
        service.eliminar(idAutorizacion);
        return ResponseEntity.noContent().build();
    }

}
