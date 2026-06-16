package cl.duoc.sigft.integracion_ms.service;

import cl.duoc.sigft.integracion_ms.dto.RegistroIntegracionDTO;
import cl.duoc.sigft.integracion_ms.dto.SolicitudIntegracionDTO;
import cl.duoc.sigft.integracion_ms.model.EstadoIntegracion;
import cl.duoc.sigft.integracion_ms.model.TipoOperacion;

import java.util.List;

public interface RegistroIntegracionService {
    RegistroIntegracionDTO enviarAArgentina(SolicitudIntegracionDTO solicitud);
    RegistroIntegracionDTO obtenerPorId(Long id);
    List<RegistroIntegracionDTO> obtenerTodos();
    List<RegistroIntegracionDTO> obtenerPorRut(String rutPasajero);
    List<RegistroIntegracionDTO> obtenerPorEstado(EstadoIntegracion estado);
    List<RegistroIntegracionDTO> obtenerPorTipoOperacion(TipoOperacion tipoOperacion);
    List<RegistroIntegracionDTO> obtenerPorTramite(String idTramiteRef);
    RegistroIntegracionDTO reintentar(Long id);
    RegistroIntegracionDTO actualizar(Long id, RegistroIntegracionDTO dto);
    void eliminar(Long id);
}
