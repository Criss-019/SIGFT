package cl.duoc.sigft.reportes_ms.controller;

import cl.duoc.sigft.reportes_ms.dto.ReporteDTO;
import cl.duoc.sigft.reportes_ms.dto.SolicitudReporteDTO;
import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;
import cl.duoc.sigft.reportes_ms.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService service;

    @PostMapping("/generar")
    public ResponseEntity<ReporteDTO> generarReporte(@Valid @RequestBody SolicitudReporteDTO solicitud) {
        log.info("Capa Controller - POST /reportes/generar - Tipo: {}", solicitud.getTipoReporte());
        return new ResponseEntity<>(service.generarReporte(solicitud), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Capa Controller - GET /reportes/{}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> obtenerTodos() {
        log.info("Capa Controller - GET /reportes - Listando todos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorTipo(@PathVariable TipoReporte tipo) {
        log.info("Capa Controller - GET /reportes/tipo/{}", tipo);
        return ResponseEntity.ok(service.obtenerPorTipo(tipo));
    }

    @GetMapping("/formato/{formato}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorFormato(@PathVariable FormatoReporte formato) {
        log.info("Capa Controller - GET /reportes/formato/{}", formato);
        return ResponseEntity.ok(service.obtenerPorFormato(formato));
    }

    @GetMapping("/solicitante/{solicitante}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorSolicitante(@PathVariable String solicitante) {
        log.info("Capa Controller - GET /reportes/solicitante/{}", solicitante);
        return ResponseEntity.ok(service.obtenerPorSolicitante(solicitante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizar(@PathVariable Long id,
                                                  @Valid @RequestBody ReporteDTO dto) {
        log.info("Capa Controller - PUT /reportes/{}", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Capa Controller - DELETE /reportes/{}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
