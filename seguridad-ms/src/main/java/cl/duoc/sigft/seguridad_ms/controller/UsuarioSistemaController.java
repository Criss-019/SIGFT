package cl.duoc.sigft.seguridad_ms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.sigft.seguridad_ms.dto.CrearUsuarioDTO;
import cl.duoc.sigft.seguridad_ms.dto.LoginRequestDTO;
import cl.duoc.sigft.seguridad_ms.dto.LoginResponseDTO;
import cl.duoc.sigft.seguridad_ms.dto.UsuarioSistemaDTO;
import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import cl.duoc.sigft.seguridad_ms.service.UsuarioSistemaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioSistemaController {

    private final UsuarioSistemaService service;

    @PostMapping
    public ResponseEntity<UsuarioSistemaDTO> crear(@Valid @RequestBody CrearUsuarioDTO dto) {
        log.info("Capa Controller - POST /usuarios - Creando usuario: {}", dto.getUsername());
        return new ResponseEntity<>(service.crearUsuario(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        log.info("Capa Controller - POST /usuarios/login - Username: {}", dto.getUsername());
        return ResponseEntity.ok(service.autenticar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioSistemaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Capa Controller - GET /usuarios/{}", id);
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioSistemaDTO> obtenerPorUsername(@PathVariable String username) {
        log.info("Capa Controller - GET /usuarios/username/{}", username);
        return ResponseEntity.ok(service.obtenerPorUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSistemaDTO>> obtenerTodos() {
        log.info("Capa Controller - GET /usuarios - Obteniendo todos");
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioSistemaDTO>> obtenerActivos() {
        log.info("Capa Controller - GET /usuarios/activos");
        return ResponseEntity.ok(service.obtenerActivos());
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<UsuarioSistemaDTO>> obtenerPorRol(@PathVariable RolUsuario rol) {
        log.info("Capa Controller - GET /usuarios/rol/{}", rol);
        return ResponseEntity.ok(service.obtenerPorRol(rol));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSistemaDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody UsuarioSistemaDTO dto) {
        log.info("Capa Controller - PUT /usuarios/{}", id);
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        log.info("Capa Controller - PATCH /usuarios/{}/desactivar", id);
        service.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Capa Controller - DELETE /usuarios/{}", id);
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
