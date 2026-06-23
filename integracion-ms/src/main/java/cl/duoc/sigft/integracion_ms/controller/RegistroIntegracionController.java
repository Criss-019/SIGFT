package cl.duoc.sigft.integracion_ms.controller;

import cl.duoc.sigft.integracion_ms.dto.RegistroIntegracionDTO;
import cl.duoc.sigft.integracion_ms.dto.SolicitudIntegracionDTO;
import cl.duoc.sigft.integracion_ms.model.EstadoIntegracion;
import cl.duoc.sigft.integracion_ms.model.TipoOperacion;
import cl.duoc.sigft.integracion_ms.service.RegistroIntegracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/integraciones")
@RequiredArgsConstructor
public class RegistroIntegracionController {

    private final RegistroIntegracionService service;

    @PostMapping("/enviar")
    public ResponseEntity<RegistroIntegracionDTO> enviarAArgentina(
            @Valid @RequestBody SolicitudIntegracionDTO solicitud) {
        log.info("Capa Controller - POST /integraciones/enviar - Tramite: {}", solicitud.getIdTramiteRef());
        return new ResponseEntity<>(service.enviarAArgentina(solicitud), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/reintentar")
    public ResponseEntity<RegistroIntegracionDTO> reintentar(@PathVariable Long id) {
        log.info("Capa Controller - POST /integraciones/{}/reintentar", id);
        return ResponseEntity.ok(service.reintentar(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroIntegracionDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Capa Controller - GET /integraciones/{}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<RegistroIntegracionDTO>> obtenerTodos() {
        log.info("Capa Controller - GET /integraciones - Listando todos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/pasajero/{rut}")
    public ResponseEntity<List<RegistroIntegracionDTO>> obtenerPorRut(@PathVariable String rut) {
        log.info("Capa Controller - GET /integraciones/pasajero/{}", rut);
        return ResponseEntity.ok(service.obtenerPorRut(rut));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RegistroIntegracionDTO>> obtenerPorEstado(
            @PathVariable EstadoIntegracion estado) {
        log.info("Capa Controller - GET /integraciones/estado/{}", estado);
        return ResponseEntity.ok(service.obtenerPorEstado(estado));
    }

    @GetMapping("/operacion/{tipo}")
    public ResponseEntity<List<RegistroIntegracionDTO>> obtenerPorTipo(
            @PathVariable TipoOperacion tipo) {
        log.info("Capa Controller - GET /integraciones/operacion/{}", tipo);
        return ResponseEntity.ok(service.obtenerPorTipoOperacion(tipo));
    }

    @GetMapping("/tramite/{idTramiteRef}")
    public ResponseEntity<List<RegistroIntegracionDTO>> obtenerPorTramite(
            @PathVariable String idTramiteRef) {
        log.info("Capa Controller - GET /integraciones/tramite/{}", idTramiteRef);
        return ResponseEntity.ok(service.obtenerPorTramite(idTramiteRef));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroIntegracionDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroIntegracionDTO dto) {
        log.info("Capa Controller - PUT /integraciones/{}", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Capa Controller - DELETE /integraciones/{}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
