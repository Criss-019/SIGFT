package cl.duoc.sigft.integracion_ms.service.impl;

import cl.duoc.sigft.integracion_ms.client.PasajerosClient;
import cl.duoc.sigft.integracion_ms.client.TramitesClient;
import cl.duoc.sigft.integracion_ms.dto.RegistroIntegracionDTO;
import cl.duoc.sigft.integracion_ms.dto.SolicitudIntegracionDTO;
import cl.duoc.sigft.integracion_ms.model.EstadoIntegracion;
import cl.duoc.sigft.integracion_ms.model.RegistroIntegracion;
import cl.duoc.sigft.integracion_ms.model.TipoOperacion;
import cl.duoc.sigft.integracion_ms.repository.RegistroIntegracionRepository;
import cl.duoc.sigft.integracion_ms.service.RegistroIntegracionService;
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
public class RegistroIntegracionServiceImpl implements RegistroIntegracionService {

    private final RegistroIntegracionRepository repository;
    private final TramitesClient tramitesClient;
    private final PasajerosClient pasajerosClient;

    @Override
    @Transactional
    public RegistroIntegracionDTO enviarAArgentina(SolicitudIntegracionDTO solicitud) {
        log.info("Capa Service - Enviando a Aduana Argentina. Tramite: {}, RUT: {}",
                solicitud.getIdTramiteRef(), solicitud.getRutPasajero());

        // Enriquecer datos consultando otros microservicios
        String datosEnviados = construirPayload(solicitud);

        RegistroIntegracion registro = RegistroIntegracion.builder()
                .idTramiteRef(solicitud.getIdTramiteRef())
                .rutPasajero(solicitud.getRutPasajero())
                .tipoOperacion(solicitud.getTipoOperacion())
                .estado(EstadoIntegracion.PENDIENTE)
                .datosEnviados(datosEnviados)
                .intentosEnvio(0)
                .build();

        registro = repository.save(registro);

        // Simulación de llamada a API Argentina (R.6) via VPN Segura
        try {
            log.info("Capa Service - Iniciando transmision VPN Segura a API Argentina. Id registro: {}", registro.getId());
            registro.setFechaEnvio(LocalDateTime.now());
            registro.setIntentosEnvio(1);

            // En producción: RestTemplate/WebClient → https://api.aduana.gob.ar/v1
            // Simulamos respuesta exitosa
            registro.setEstado(EstadoIntegracion.CONFIRMADO);
            registro.setFechaRespuesta(LocalDateTime.now());
            registro.setCodigoRespuestaArgentina("AR-OK-200");
            registro.setMensajeRespuesta("Tramite recibido y confirmado por Aduana Argentina");

            log.info("Capa Service - Respuesta confirmada de Aduana Argentina para registro: {}", registro.getId());
        } catch (Exception e) {
            log.error("Capa Service - Error al comunicar con API Argentina: {}", e.getMessage());
            registro.setEstado(EstadoIntegracion.ERROR);
            registro.setMensajeRespuesta("Error de comunicación: " + e.getMessage());
        }

        repository.save(registro);
        return mapearADto(registro);
    }

    @Override
    @Transactional(readOnly = true)
    public RegistroIntegracionDTO obtenerPorId(Long id) {
        log.info("Capa Service - Buscando registro de integracion id: {}", id);
        RegistroIntegracion registro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de integración no encontrado con id: " + id));
        return mapearADto(registro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroIntegracionDTO> obtenerTodos() {
        log.info("Capa Service - Obteniendo todos los registros de integracion");
        return repository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroIntegracionDTO> obtenerPorRut(String rutPasajero) {
        log.info("Capa Service - Buscando registros por RUT: {}", rutPasajero);
        return repository.findByRutPasajero(rutPasajero).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroIntegracionDTO> obtenerPorEstado(EstadoIntegracion estado) {
        log.info("Capa Service - Buscando registros por estado: {}", estado);
        return repository.findByEstado(estado).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroIntegracionDTO> obtenerPorTipoOperacion(TipoOperacion tipoOperacion) {
        log.info("Capa Service - Buscando registros por tipo de operacion: {}", tipoOperacion);
        return repository.findByTipoOperacion(tipoOperacion).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroIntegracionDTO> obtenerPorTramite(String idTramiteRef) {
        log.info("Capa Service - Buscando registros por tramite ref: {}", idTramiteRef);
        return repository.findByIdTramiteRef(idTramiteRef).stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RegistroIntegracionDTO reintentar(Long id) {
        log.info("Capa Service - Reintentando envio para registro id: {}", id);
        RegistroIntegracion registro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con id: " + id));

        if (!registro.puedeReintentar()) {
            log.warn("Capa Service - Registro {} no puede reintentar. Estado: {}, Intentos: {}",
                    id, registro.getEstado(), registro.getIntentosEnvio());
            throw new IllegalStateException("El registro no puede ser reintentado. "
                    + "Estado: " + registro.getEstado() + ", Intentos: " + registro.getIntentosEnvio());
        }

        registro.setEstado(EstadoIntegracion.PENDIENTE);
        registro.setIntentosEnvio(registro.getIntentosEnvio() + 1);
        registro.setFechaEnvio(LocalDateTime.now());

        // Simulación reintento
        registro.setEstado(EstadoIntegracion.CONFIRMADO);
        registro.setFechaRespuesta(LocalDateTime.now());
        registro.setCodigoRespuestaArgentina("AR-OK-200");
        registro.setMensajeRespuesta("Reintento exitoso - Confirmado por Aduana Argentina");

        repository.save(registro);
        log.info("Capa Service - Reintento exitoso para registro id: {}", id);
        return mapearADto(registro);
    }

    @Override
    @Transactional
    public RegistroIntegracionDTO actualizar(Long id, RegistroIntegracionDTO dto) {
        log.info("Capa Service - Actualizando registro integracion id: {}", id);
        RegistroIntegracion registro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con id: " + id));

        registro.setEstado(dto.getEstado());
        registro.setMensajeRespuesta(dto.getMensajeRespuesta());
        registro.setCodigoRespuestaArgentina(dto.getCodigoRespuestaArgentina());

        repository.save(registro);
        log.info("Capa Service - Registro integracion actualizado id: {}", id);
        return mapearADto(registro);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Capa Service - Eliminando registro integracion id: {}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con id: " + id);
        }
        repository.deleteById(id);
        log.info("Capa Service - Registro integracion eliminado id: {}", id);
    }

    private String construirPayload(SolicitudIntegracionDTO solicitud) {
        StringBuilder payload = new StringBuilder();
        payload.append("{\"tramiteRef\":\"").append(solicitud.getIdTramiteRef()).append("\",");
        payload.append("\"rut\":\"").append(solicitud.getRutPasajero()).append("\",");
        payload.append("\"operacion\":\"").append(solicitud.getTipoOperacion()).append("\"");

        // Enriquecer con datos de tramites-ms
        try {
            Map<String, Object> tramite = tramitesClient.obtenerTramitePorId(solicitud.getIdTramiteRef());
            if (tramite != null) {
                payload.append(",\"tramite\":").append(tramite.toString());
                log.info("Capa Service - Datos tramite incluidos en payload");
            }
        } catch (Exception e) {
            log.warn("Capa Service - No se pudo obtener datos de tramites-ms: {}", e.getMessage());
        }

        // Enriquecer con datos de pasajeros-ms
        try {
            Map<String, Object> pasajero = pasajerosClient.obtenerPasajeroPorRut(solicitud.getRutPasajero());
            if (pasajero != null) {
                payload.append(",\"pasajero\":").append(pasajero.toString());
                log.info("Capa Service - Datos pasajero incluidos en payload");
            }
        } catch (Exception e) {
            log.warn("Capa Service - No se pudo obtener datos de pasajeros-ms: {}", e.getMessage());
        }

        if (solicitud.getDatosAdicionales() != null) {
            payload.append(",\"adicionales\":\"").append(solicitud.getDatosAdicionales()).append("\"");
        }
        payload.append("}");
        return payload.toString();
    }

    private RegistroIntegracionDTO mapearADto(RegistroIntegracion r) {
        return RegistroIntegracionDTO.builder()
                .id(r.getId())
                .idTramiteRef(r.getIdTramiteRef())
                .rutPasajero(r.getRutPasajero())
                .tipoOperacion(r.getTipoOperacion())
                .estado(r.getEstado())
                .fechaEnvio(r.getFechaEnvio())
                .fechaRespuesta(r.getFechaRespuesta())
                .codigoRespuestaArgentina(r.getCodigoRespuestaArgentina())
                .mensajeRespuesta(r.getMensajeRespuesta())
                .datosEnviados(r.getDatosEnviados())
                .intentosEnvio(r.getIntentosEnvio())
                .build();
    }
}
