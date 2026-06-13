package cl.duoc.sigft.tramites_ms.service;

import java.util.List;

import cl.duoc.sigft.tramites_ms.dto.AutorizacionNotarialDTO;

public interface AutorizacionNotarialService {
    AutorizacionNotarialDTO registrar(AutorizacionNotarialDTO dto);
    AutorizacionNotarialDTO obtenerPorId(String id);
    List<AutorizacionNotarialDTO> obtenerTodas();
    void eliminar(String id);
}
