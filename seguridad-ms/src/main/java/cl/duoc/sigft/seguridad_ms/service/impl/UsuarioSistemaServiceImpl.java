package cl.duoc.sigft.seguridad_ms.service.impl;

import cl.duoc.sigft.seguridad_ms.dto.CrearUsuarioDTO;
import cl.duoc.sigft.seguridad_ms.dto.LoginRequestDTO;
import cl.duoc.sigft.seguridad_ms.dto.UsuarioSistemaDTO;
import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import cl.duoc.sigft.seguridad_ms.model.UsuarioSistema;
import cl.duoc.sigft.seguridad_ms.repository.UsuarioSistemaRepository;
import cl.duoc.sigft.seguridad_ms.service.UsuarioSistemaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioSistemaServiceImpl implements UsuarioSistemaService {

    private final UsuarioSistemaRepository repository;

    @Override
    @Transactional
    public UsuarioSistemaDTO crearUsuario(CrearUsuarioDTO dto) {
        log.info("Capa Service - Creando usuario con username: {}", dto.getUsername());

        if (repository.existsByUsername(dto.getUsername())) {
            log.warn("Capa Service - Username ya existe: {}", dto.getUsername());
            throw new IllegalArgumentException("El username ya está en uso: " + dto.getUsername());
        }
        if (repository.existsByEmail(dto.getEmail())) {
            log.warn("Capa Service - Email ya existe: {}", dto.getEmail());
            throw new IllegalArgumentException("El email ya está registrado: " + dto.getEmail());
        }

        // Hash simulado; en producción usar BCryptPasswordEncoder
        String hashSimulado = "$2a$12$hash_" + dto.getPassword().hashCode();

        UsuarioSistema usuario = UsuarioSistema.builder()
                .username(dto.getUsername())
                .passwordHash(hashSimulado)
                .nombreCompleto(dto.getNombreCompleto())
                .email(dto.getEmail())
                .rol(dto.getRol())
                .activo(true)
                .fechaCreacion(LocalDateTime.now())
                .build();

        UsuarioSistema guardado = repository.save(usuario);
        log.info("Capa Service - Usuario creado con id: {}", guardado.getId());
        return mapearADto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioSistemaDTO obtenerPorId(Long id) {
        log.info("Capa Service - Buscando usuario por id: {}", id);
        UsuarioSistema usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return mapearADto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioSistemaDTO obtenerPorUsername(String username) {
        log.info("Capa Service - Buscando usuario por username: {}", username);
        UsuarioSistema usuario = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        return mapearADto(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioSistemaDTO> obtenerTodos() {
        log.info("Capa Service - Obteniendo todos los usuarios");
        return repository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioSistemaDTO> obtenerPorRol(RolUsuario rol) {
        log.info("Capa Service - Buscando usuarios por rol: {}", rol);
        return repository.findByRol(rol).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioSistemaDTO> obtenerActivos() {
        log.info("Capa Service - Obteniendo usuarios activos");
        return repository.findByActivoTrue().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioSistemaDTO actualizar(Long id, UsuarioSistemaDTO dto) {
        log.info("Capa Service - Actualizando usuario id: {}", id);
        UsuarioSistema usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.isActivo());

        repository.save(usuario);
        log.info("Capa Service - Usuario actualizado id: {}", id);
        return mapearADto(usuario);
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        log.info("Capa Service - Desactivando cuenta usuario id: {}", id);
        UsuarioSistema usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuario.setActivo(false);
        repository.save(usuario);
        log.info("Capa Service - Usuario desactivado id: {}", id);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Capa Service - Eliminando usuario id: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        repository.deleteById(id);
        log.info("Capa Service - Usuario eliminado id: {}", id);
    }

    @Override
    @Transactional
    public UsuarioSistemaDTO autenticar(LoginRequestDTO loginRequest) {
        log.info("Capa Service - Intento de autenticacion para usuario: {}", loginRequest.getUsername());
        UsuarioSistema usuario = repository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!usuario.esCuentaHabilitada()) {
            log.warn("Capa Service - Cuenta desactivada: {}", loginRequest.getUsername());
            throw new RuntimeException("La cuenta está desactivada. Contacte al administrador.");
        }

        // En producción usar BCrypt: passwordEncoder.matches(raw, hash)
        String hashIngresado = "$2a$12$hash_" + loginRequest.getPassword().hashCode();
        if (!hashIngresado.equals(usuario.getPasswordHash())) {
            log.warn("Capa Service - Contraseña incorrecta para usuario: {}", loginRequest.getUsername());
            throw new RuntimeException("Credenciales inválidas");
        }

        usuario.setFechaUltimoAcceso(LocalDateTime.now());
        repository.save(usuario);
        log.info("Capa Service - Autenticacion exitosa para usuario: {}", loginRequest.getUsername());
        return mapearADto(usuario);
    }

    private UsuarioSistemaDTO mapearADto(UsuarioSistema u) {
        return UsuarioSistemaDTO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .nombreCompleto(u.getNombreCompleto())
                .email(u.getEmail())
                .rol(u.getRol())
                .activo(u.isActivo())
                .fechaCreacion(u.getFechaCreacion())
                .fechaUltimoAcceso(u.getFechaUltimoAcceso())
                .build();
    }
}
