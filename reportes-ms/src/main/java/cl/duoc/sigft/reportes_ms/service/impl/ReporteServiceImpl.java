package cl.duoc.sigft.reportes_ms.service.impl;

import cl.duoc.sigft.reportes_ms.client.TramitesClient;
import cl.duoc.sigft.reportes_ms.dto.ReporteDTO;
import cl.duoc.sigft.reportes_ms.dto.SolicitudReporteDTO;
import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.Reporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;
import cl.duoc.sigft.reportes_ms.repository.ReporteRepository;
import cl.duoc.sigft.reportes_ms.service.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository repository;
    private final TramitesClient tramitesClient;

    @Override
    @Transactional
    public ReporteDTO generarReporte(SolicitudReporteDTO solicitud) {
        log.info("Capa Service - Generando reporte tipo: {} formato: {}",
                solicitud.getTipoReporte(), solicitud.getFormato());

        if (solicitud.getFechaDesde().isAfter(solicitud.getFechaHasta())) {
            log.warn("Capa Service - Rango de fechas inválido");
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a la fecha hasta");
        }

        // Consultar datos de tramites-ms via Feign para calcular total
        int totalRegistros = 0;
        try {
            List<Map<String, Object>> tramites = tramitesClient.obtenerTodosTramites();
            totalRegistros = tramites != null ? tramites.size() : 0;
            log.info("Capa Service - Tramites consultados via Feign: {}", totalRegistros);
        } catch (Exception e) {
            log.warn("Capa Service - No se pudo conectar con tramites-ms: {}", e.getMessage());
        }

        String nombreArchivo = String.format("SIGFT_%s_%s_%s.%s",
                solicitud.getTipoReporte(),
                solicitud.getFechaDesde(),
                solicitud.getFechaHasta(),
                solicitud.getFormato().name().toLowerCase());

        Reporte reporte = Reporte.builder()
                .tipoReporte(solicitud.getTipoReporte())
                .formato(solicitud.getFormato())
                .fechaDesde(solicitud.getFechaDesde())
                .fechaHasta(solicitud.getFechaHasta())
                .totalRegistros(totalRegistros)
                .nombreArchivo(nombreArchivo)
                .generadoPor(solicitud.getSolicitante())
                .fechaGeneracion(LocalDateTime.now())
                .observaciones(solicitud.getObservaciones())
                .build();

        Reporte guardado = repository.save(reporte);
        log.info("Capa Service - Reporte generado con id: {}", guardado.getId());
        return mapearADto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteDTO obtenerPorId(Long id) {
        log.info("Capa Service - Buscando reporte por id: {}", id);
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        return mapearADto(reporte);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerTodos() {
        log.info("Capa Service - Obteniendo todos los reportes");
        return repository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorTipo(TipoReporte tipo) {
        log.info("Capa Service - Buscando reportes por tipo: {}", tipo);
        return repository.findByTipoReporte(tipo).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorFormato(FormatoReporte formato) {
        log.info("Capa Service - Buscando reportes por formato: {}", formato);
        return repository.findByFormato(formato).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorSolicitante(String solicitante) {
        log.info("Capa Service - Buscando reportes por solicitante: {}", solicitante);
        return repository.findByGeneradoPor(solicitante).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReporteDTO actualizar(Long id, ReporteDTO dto) {
        log.info("Capa Service - Actualizando reporte id: {}", id);
        Reporte reporte = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
        reporte.setObservaciones(dto.getObservaciones());
        reporte.setNombreArchivo(dto.getNombreArchivo());
        repository.save(reporte);
        log.info("Capa Service - Reporte actualizado id: {}", id);
        return mapearADto(reporte);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Capa Service - Eliminando reporte id: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Reporte no encontrado con id: " + id);
        }
        repository.deleteById(id);
        log.info("Capa Service - Reporte eliminado id: {}", id);
    }

    private ReporteDTO mapearADto(Reporte r) {
        return ReporteDTO.builder()
                .id(r.getId())
                .tipoReporte(r.getTipoReporte())
                .formato(r.getFormato())
                .fechaDesde(r.getFechaDesde())
                .fechaHasta(r.getFechaHasta())
                .totalRegistros(r.getTotalRegistros())
                .nombreArchivo(r.getNombreArchivo())
                .generadoPor(r.getGeneradoPor())
                .fechaGeneracion(r.getFechaGeneracion())
                .observaciones(r.getObservaciones())
                .build();
    }
}
