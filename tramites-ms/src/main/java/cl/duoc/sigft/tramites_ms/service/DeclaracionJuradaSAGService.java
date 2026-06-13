package cl.duoc.sigft.tramites_ms.service;

import java.util.List;

import cl.duoc.sigft.tramites_ms.dto.DeclaracionJuradaSAGDTO;

public interface DeclaracionJuradaSAGService {
    DeclaracionJuradaSAGDTO registrar(DeclaracionJuradaSAGDTO dto);
    DeclaracionJuradaSAGDTO obtenerPorId(String id);
    List<DeclaracionJuradaSAGDTO> obtenerTodas();
    void eliminar(String id);
}
