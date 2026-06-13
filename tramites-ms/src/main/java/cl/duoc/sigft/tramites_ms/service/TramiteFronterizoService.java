package cl.duoc.sigft.tramites_ms.service;

import java.util.List;

import cl.duoc.sigft.tramites_ms.dto.TramiteFronterizoDTO;

public interface TramiteFronterizoService {
    TramiteFronterizoDTO registrar(TramiteFronterizoDTO dto);
    TramiteFronterizoDTO procesarTramite(String idTramite);
    TramiteFronterizoDTO obtenerPorId(String idTramite);
    List<TramiteFronterizoDTO> obtenerTodos();
    void eliminar(String idTramite);

}
