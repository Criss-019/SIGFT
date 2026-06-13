package cl.duoc.sigft.tramites_ms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar la interfaz

import cl.duoc.sigft.tramites_ms.client.PasajeroClient;
import cl.duoc.sigft.tramites_ms.dto.TramiteFronterizoDTO;
import cl.duoc.sigft.tramites_ms.model.TramiteFronterizo;
import cl.duoc.sigft.tramites_ms.repository.TramiteFronterizoRepository;
import cl.duoc.sigft.tramites_ms.service.TramiteFronterizoService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TramiteFronterizoServiceImpl implements TramiteFronterizoService {
    private final TramiteFronterizoRepository repository;
    private final PasajeroClient feignClient;

    @Override
    @Transactional
    public TramiteFronterizoDTO registrar(TramiteFronterizoDTO dto) {
        log.info("Capa Service - Intentando registrar trámite: {}", dto.getIdTramite());
        
        // 0. Validar si el trámite ya existe para evitar errores de llave primaria
        if (repository.existsById(dto.getIdTramite())) {
            log.error("El trámite {} ya existe en la base de datos.", dto.getIdTramite());
            throw new IllegalArgumentException("El trámite con ID " + dto.getIdTramite() + " ya existe.");
        }

        // 1. Validar Pasajeros vía Feign (Obligatorio)
        if (dto.getRutsPasajeros() == null || dto.getRutsPasajeros().isEmpty()) {
            throw new IllegalArgumentException("El trámite debe contener al menos un pasajero.");
        }

        for (String rut : dto.getRutsPasajeros()) {
            try {
                log.info("Validando pasajero RUT {} vía OpenFeign...", rut);
                feignClient.obtenerPasajeroPorRut(rut);
            } catch (FeignException.NotFound e) {
                log.error("Validación fallida: Pasajero RUT {} no existe en pasajeros-ms", rut);
                throw new IllegalArgumentException("El pasajero con RUT " + rut + " no existe.");
            }
        }

        // 2. Validar Vehículos vía Feign (Opcional - Manejamos posible Null)
        List<String> patentesSeguras = dto.getPatentesVehiculos() != null ? dto.getPatentesVehiculos() : new ArrayList<>();
        for (String patente : patentesSeguras) {
            try {
                log.info("Validando vehículo patente {} vía OpenFeign...", patente);
                feignClient.obtenerVehiculoPorPatente(patente);
            } catch (FeignException.NotFound e) {
                log.error("Validación fallida: Vehículo patente {} no existe en pasajeros-ms", patente);
                throw new IllegalArgumentException("El vehículo con patente " + patente + " no existe.");
            }
        }

        // 3. Construir y guardar
        TramiteFronterizo tramite = TramiteFronterizo.builder()
                .idTramite(dto.getIdTramite())
                .fechaHora(dto.getFechaHora())
                .estadoTramite("PENDIENTE") // Estado inicial por regla de negocio
                .aduanaOrigen(dto.getAduanaOrigen())
                .aduanaDestino(dto.getAduanaDestino())
                .rutsPasajeros(dto.getRutsPasajeros())
                .patentesVehiculos(patentesSeguras)
                .build();

        TramiteFronterizo guardado = repository.save(tramite);
        log.info("Capa Service - Trámite {} guardado con éxito.", guardado.getIdTramite());
        return mapearADto(guardado);
    }

    @Override
    @Transactional
    public TramiteFronterizoDTO procesarTramite(String idTramite) {
        log.info("Capa Service - Procesando cruce para trámite: {}", idTramite);
        TramiteFronterizo tramite = repository.findById(idTramite)
                .orElseThrow(() -> new RuntimeException("Trámite no encontrado para procesar."));
        
        tramite.procesarCruce(); // Ejecuta lógica de cambio de estado dictada por tu diagrama
        return mapearADto(repository.save(tramite));
    }
    @Override
    @Transactional(readOnly = true)
    public TramiteFronterizoDTO obtenerPorId(String idTramite) {
        log.info("Capa Service - Buscando trámite ID: {}", idTramite);
        TramiteFronterizo tramite = repository.findById(idTramite)
                .orElseThrow(() -> new RuntimeException("Trámite no encontrado."));
        return mapearADto(tramite);
    }
    @Override
    @Transactional(readOnly = true)
    public List<TramiteFronterizoDTO> obtenerTodos() {
        log.info("Capa Service - Obteniendo la lista completa de trámites.");
        return repository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(String idTramite) {
        log.info("Capa Service - Eliminando trámite ID: {}", idTramite);
        if (!repository.existsById(idTramite)) {
            throw new RuntimeException("Trámite no encontrado para eliminar.");
        }
        repository.deleteById(idTramite);
        log.info("Trámite {} eliminado correctamente.", idTramite);
    }

    // Método utilitario para convertir Entidad a DTO
    private TramiteFronterizoDTO mapearADto(TramiteFronterizo entity) {
        TramiteFronterizoDTO dto = new TramiteFronterizoDTO();
        dto.setIdTramite(entity.getIdTramite());
        dto.setFechaHora(entity.getFechaHora());
        dto.setEstadoTramite(entity.getEstadoTramite());
        dto.setAduanaOrigen(entity.getAduanaOrigen());
        dto.setAduanaDestino(entity.getAduanaDestino());
        dto.setRutsPasajeros(entity.getRutsPasajeros());
        dto.setPatentesVehiculos(entity.getPatentesVehiculos());
        return dto;
    }

}
