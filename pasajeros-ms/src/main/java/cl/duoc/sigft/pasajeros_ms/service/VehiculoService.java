package cl.duoc.sigft.pasajeros_ms.service;

import java.util.List;

import cl.duoc.sigft.pasajeros_ms.dto.VehiculoDTO;

public interface VehiculoService {
    VehiculoDTO registrar(VehiculoDTO dto);
    VehiculoDTO obtenerPorPatente(String patente);
    List<VehiculoDTO> obtenerTodos();
    List<VehiculoDTO> obtenerPorRutPropietario(String rutPropietario);
    VehiculoDTO actualizar(String patente, VehiculoDTO dto);
    void eliminar(String patente);
}
