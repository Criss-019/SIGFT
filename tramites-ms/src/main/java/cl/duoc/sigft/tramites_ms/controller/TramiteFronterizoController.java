package cl.duoc.sigft.tramites_ms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.sigft.tramites_ms.dto.TramiteFronterizoDTO;
import cl.duoc.sigft.tramites_ms.service.TramiteFronterizoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tramites")
@RequiredArgsConstructor
public class TramiteFronterizoController {
    // Se inyecta la interfaz
    private final TramiteFronterizoService service;

    @PostMapping
    public ResponseEntity<TramiteFronterizoDTO> registrar(@Valid @RequestBody TramiteFronterizoDTO dto) {
        log.info("REST Request - POST /api/v1/tramites");
        TramiteFronterizoDTO response = service.registrar(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/procesar")
    public ResponseEntity<TramiteFronterizoDTO> procesarCruce(@PathVariable("id") String idTramite) {
        log.info("REST Request - PATCH /api/v1/tramites/{}/procesar", idTramite);
        TramiteFronterizoDTO response = service.procesarTramite(idTramite);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramiteFronterizoDTO> obtenerPorId(@PathVariable("id") String idTramite) {
        log.info("REST Request - GET /api/v1/tramites/{}", idTramite);
        TramiteFronterizoDTO response = service.obtenerPorId(idTramite);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TramiteFronterizoDTO>> obtenerTodos() {
        log.info("REST Request - GET /api/v1/tramites");
        List<TramiteFronterizoDTO> response = service.obtenerTodos();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") String idTramite) {
        log.info("REST Request - DELETE /api/v1/tramites/{}", idTramite);
        service.eliminar(idTramite);
        return ResponseEntity.noContent().build();
    }

}
