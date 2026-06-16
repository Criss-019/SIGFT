package cl.duoc.sigft.reportes_ms.service;

import cl.duoc.sigft.reportes_ms.dto.ReporteDTO;
import cl.duoc.sigft.reportes_ms.dto.SolicitudReporteDTO;
import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;

import java.util.List;

public interface ReporteService {
    ReporteDTO generarReporte(SolicitudReporteDTO solicitud);
    ReporteDTO obtenerPorId(Long id);
    List<ReporteDTO> obtenerTodos();
    List<ReporteDTO> obtenerPorTipo(TipoReporte tipo);
    List<ReporteDTO> obtenerPorFormato(FormatoReporte formato);
    List<ReporteDTO> obtenerPorSolicitante(String solicitante);
    ReporteDTO actualizar(Long id, ReporteDTO dto);
    void eliminar(Long id);
}
