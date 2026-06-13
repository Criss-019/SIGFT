package cl.duoc.sigft.pasajeros_ms.service;

import java.util.List;

import cl.duoc.sigft.pasajeros_ms.dto.PasajeroDTO;

public interface PasajeroService {
    PasajeroDTO registrar(PasajeroDTO dto);
    PasajeroDTO obtenerPorRut(String rut);
    List<PasajeroDTO> obtenerTodos();
    PasajeroDTO actualizar(String rut, PasajeroDTO dto);
    void eliminar(String rut);
}
