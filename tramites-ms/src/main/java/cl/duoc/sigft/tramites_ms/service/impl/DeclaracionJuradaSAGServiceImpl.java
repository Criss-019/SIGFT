package cl.duoc.sigft.tramites_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.sigft.tramites_ms.client.PasajeroClient;
import cl.duoc.sigft.tramites_ms.dto.DeclaracionJuradaSAGDTO;
import cl.duoc.sigft.tramites_ms.model.DeclaracionJuradaSAG;
import cl.duoc.sigft.tramites_ms.repository.DeclaracionJuradaSAGRepository;
import cl.duoc.sigft.tramites_ms.service.DeclaracionJuradaSAGService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeclaracionJuradaSAGServiceImpl implements DeclaracionJuradaSAGService  {
    private final DeclaracionJuradaSAGRepository repository;
    private final PasajeroClient feignClient;

    @Override
    @Transactional
    public DeclaracionJuradaSAGDTO registrar(DeclaracionJuradaSAGDTO dto) {
        log.info("Capa Service - Registrando Declaración SAG con ID: {}", dto.getIdDeclaracion());

        // 1. Validar si la declaración ya existe
        if (repository.existsById(dto.getIdDeclaracion())) {
            log.error("La declaración con ID {} ya existe.", dto.getIdDeclaracion());
            throw new IllegalArgumentException("La declaración con ID " + dto.getIdDeclaracion() + " ya existe.");
        }

        // 2. Validar existencia del pasajero vía Feign Client
        try {
            log.info("Validando pasajero RUT {} vía OpenFeign...", dto.getRutPasajero());
            feignClient.obtenerPasajeroPorRut(dto.getRutPasajero());
        } catch (FeignException.NotFound e) {
            log.error("Pasajero RUT {} no existe en el sistema.", dto.getRutPasajero());
            throw new IllegalArgumentException("El pasajero con RUT " + dto.getRutPasajero() + " no existe. Registre al pasajero primero.");
        }

        // 3. Mapear DTO a Entidad
        DeclaracionJuradaSAG declaracion = DeclaracionJuradaSAG.builder()
                .idDeclaracion(dto.getIdDeclaracion())
                .fechaRegistro(dto.getFechaRegistro())
                .traeProductosAnimales(dto.isTraeProductosAnimales())
                .traeProductosVegetales(dto.isTraeProductosVegetales())
                .poseeMascotas(dto.isPoseeMascotas())
                .rutPasajero(dto.getRutPasajero())
                .build();

        // 4. Aplicar regla de negocio (Valida si trae productos que requieran revisión)
        declaracion.validarDeclaracion();

        // 5. Guardar en base de datos y retornar
        DeclaracionJuradaSAG guardada = repository.save(declaracion);
        log.info("Declaración SAG {} registrada con éxito.", guardada.getIdDeclaracion());
        return mapearADto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public DeclaracionJuradaSAGDTO obtenerPorId(String idDeclaracion) {
        log.info("Capa Service - Buscando Declaración SAG con ID: {}", idDeclaracion);
        DeclaracionJuradaSAG entidad = repository.findById(idDeclaracion)
                .orElseThrow(() -> new RuntimeException("Declaración SAG no encontrada con ID: " + idDeclaracion));
        return mapearADto(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeclaracionJuradaSAGDTO> obtenerTodas() {
        log.info("Capa Service - Obteniendo todas las declaraciones SAG.");
        return repository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(String idDeclaracion) {
        log.info("Capa Service - Intentando eliminar Declaración SAG con ID: {}", idDeclaracion);
        if (!repository.existsById(idDeclaracion)) {
            throw new RuntimeException("No se puede eliminar. Declaración SAG no encontrada con ID: " + idDeclaracion);
        }
        repository.deleteById(idDeclaracion);
        log.info("Declaración SAG {} eliminada con éxito.", idDeclaracion);
    }

    // Método utilitario privado
    private DeclaracionJuradaSAGDTO mapearADto(DeclaracionJuradaSAG entidad) {
        DeclaracionJuradaSAGDTO dto = new DeclaracionJuradaSAGDTO();
        dto.setIdDeclaracion(entidad.getIdDeclaracion());
        dto.setFechaRegistro(entidad.getFechaRegistro());
        dto.setTraeProductosAnimales(entidad.isTraeProductosAnimales());
        dto.setTraeProductosVegetales(entidad.isTraeProductosVegetales());
        dto.setPoseeMascotas(entidad.isPoseeMascotas());
        dto.setRutPasajero(entidad.getRutPasajero());
        return dto;
    }
}
