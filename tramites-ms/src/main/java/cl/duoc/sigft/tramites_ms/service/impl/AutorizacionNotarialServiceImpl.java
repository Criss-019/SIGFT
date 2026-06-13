package cl.duoc.sigft.tramites_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.sigft.tramites_ms.client.PasajeroClient;
import cl.duoc.sigft.tramites_ms.dto.AutorizacionNotarialDTO;
import cl.duoc.sigft.tramites_ms.model.AutorizacionNotarial;
import cl.duoc.sigft.tramites_ms.repository.AutorizacionNotarialRepository;
import cl.duoc.sigft.tramites_ms.service.AutorizacionNotarialService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutorizacionNotarialServiceImpl implements AutorizacionNotarialService{
    private final AutorizacionNotarialRepository repository;
    private final PasajeroClient feignClient;

    @Override
    @Transactional
    public AutorizacionNotarialDTO registrar(AutorizacionNotarialDTO dto) {
        log.info("Capa Service - Registrando Autorización Notarial con ID: {}", dto.getIdAutorizacion());

        // 1. Validar si la autorización ya existe
        if (repository.existsById(dto.getIdAutorizacion())) {
            log.error("La autorización con ID {} ya existe.", dto.getIdAutorizacion());
            throw new IllegalArgumentException("La autorización notarial con ID " + dto.getIdAutorizacion() + " ya existe.");
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
        AutorizacionNotarial autorizacion = AutorizacionNotarial.builder()
                .idAutorizacion(dto.getIdAutorizacion())
                .fechaEmision(dto.getFechaEmision())
                .notariaOrigen(dto.getNotariaOrigen())
                .adjuntoPDF(dto.getAdjuntoPDF())
                .rutPasajero(dto.getRutPasajero())
                .build();

        // 4. Aplicar regla de negocio de vigencia (máximo 30 días de antigüedad)
        if (!autorizacion.validarVigencia()) {
            log.error("La autorización notarial {} ha expirado.", dto.getIdAutorizacion());
            throw new IllegalStateException("La autorización notarial ha expirado o tiene una fecha inválida (no puede superar los 30 días de antigüedad).");
        }

        // 5. Guardar en base de datos y retornar
        AutorizacionNotarial guardada = repository.save(autorizacion);
        log.info("Autorización Notarial {} registrada con éxito.", guardada.getIdAutorizacion());
        return mapearADto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public AutorizacionNotarialDTO obtenerPorId(String idAutorizacion) {
        log.info("Capa Service - Buscando Autorización Notarial con ID: {}", idAutorizacion);
        AutorizacionNotarial entidad = repository.findById(idAutorizacion)
                .orElseThrow(() -> new RuntimeException("Autorización Notarial no encontrada con ID: " + idAutorizacion));
        return mapearADto(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutorizacionNotarialDTO> obtenerTodas() {
        log.info("Capa Service - Obteniendo todas las autorizaciones notariales.");
        return repository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(String idAutorizacion) {
        log.info("Capa Service - Intentando eliminar Autorización Notarial con ID: {}", idAutorizacion);
        if (!repository.existsById(idAutorizacion)) {
            throw new RuntimeException("No se puede eliminar. Autorización Notarial no encontrada con ID: " + idAutorizacion);
        }
        repository.deleteById(idAutorizacion);
        log.info("Autorización Notarial {} eliminada con éxito.", idAutorizacion);
    }

    // Método utilitario privado
    private AutorizacionNotarialDTO mapearADto(AutorizacionNotarial entidad) {
        AutorizacionNotarialDTO dto = new AutorizacionNotarialDTO();
        dto.setIdAutorizacion(entidad.getIdAutorizacion());
        dto.setFechaEmision(entidad.getFechaEmision());
        dto.setNotariaOrigen(entidad.getNotariaOrigen());
        dto.setAdjuntoPDF(entidad.getAdjuntoPDF());
        dto.setRutPasajero(entidad.getRutPasajero());
        return dto;
    }

}
