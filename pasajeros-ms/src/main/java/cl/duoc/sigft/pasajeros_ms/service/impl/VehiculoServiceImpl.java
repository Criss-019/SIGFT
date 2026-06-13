package cl.duoc.sigft.pasajeros_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.sigft.pasajeros_ms.dto.VehiculoDTO;
import cl.duoc.sigft.pasajeros_ms.model.Pasajero;
import cl.duoc.sigft.pasajeros_ms.model.Vehiculo;
import cl.duoc.sigft.pasajeros_ms.repository.PasajeroRepository;
import cl.duoc.sigft.pasajeros_ms.repository.VehiculoRepository;
import cl.duoc.sigft.pasajeros_ms.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final PasajeroRepository pasajeroRepository; // Para validar que el dueño exista

    @Override
    @Transactional
    public VehiculoDTO registrar(VehiculoDTO dto) {
        log.info("Capa Service - Registrando vehículo patente: {}", dto.getPatente());
        
        if (vehiculoRepository.existsById(dto.getPatente())) {
            throw new IllegalArgumentException("El vehículo con esta patente ya existe");
        }

        // 1. BUSCAMOS EL OBJETO PASAJERO (Relación JPA)
        Pasajero pasajeroDB = pasajeroRepository.findById(dto.getRutPasajero())
                .orElseThrow(() -> new IllegalArgumentException("El pasajero especificado no existe. Registre al pasajero primero."));

        // 2. CONSTRUIMOS EL VEHÍCULO CON SU DUEÑO
        Vehiculo vehiculo = Vehiculo.builder()
                .patente(dto.getPatente())
                .tipoVehiculo(dto.getTipoVehiculo())
                .esDiplomatico(dto.getEsDiplomatico())
                .plazoMaximoDias(dto.getPlazoMaximoDias())
                .pasajero(pasajeroDB) // Asignación del objeto relacional
                .build();

        // 3. IMPLEMENTACIÓN DE LA LÓGICA DEL DIAGRAMA
        // Forzamos el plazo calculando si es diplomático o no
        int plazoCalculado = vehiculo.calcularPlazo();
        vehiculo.setPlazoMaximoDias(plazoCalculado);
        log.info("Capa Service - Plazo máximo calculado para el vehículo: {} días", plazoCalculado);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return mapearADto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoDTO obtenerPorPatente(String patente) {
        Vehiculo vehiculo = vehiculoRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return mapearADto(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> obtenerTodos() {
        return vehiculoRepository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> obtenerPorRutPropietario(String rut) {
        // En un @ManyToOne, podemos buscar iterando o con un método personalizado en el repositorio
        return vehiculoRepository.findByPasajeroRut(rut).stream()
            .map(this::mapearADto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehiculoDTO actualizar(String patente, VehiculoDTO dto) {
        Vehiculo vehiculo = vehiculoRepository.findById(patente)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado para actualizar"));

        vehiculo.setTipoVehiculo(dto.getTipoVehiculo());
        vehiculo.setEsDiplomatico(dto.getEsDiplomatico());
        
        // Recalculamos el plazo en caso de que su estatus diplomático haya cambiado
        vehiculo.setPlazoMaximoDias(dto.getPlazoMaximoDias());
        vehiculo.setPlazoMaximoDias(vehiculo.calcularPlazo());

        vehiculoRepository.save(vehiculo);
        return mapearADto(vehiculo);
    }

    @Override
    @Transactional
    public void eliminar(String patente) {
        if (!vehiculoRepository.existsById(patente)) {
            throw new RuntimeException("Vehículo no encontrado para eliminar");
        }
        vehiculoRepository.deleteById(patente);
    }

    // MAPEO ACTUALIZADO PARA EXTRAER EL RUT DESDE EL OBJETO PASAJERO
    private VehiculoDTO mapearADto(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPatente(vehiculo.getPatente());
        dto.setTipoVehiculo(vehiculo.getTipoVehiculo());
        dto.setEsDiplomatico(vehiculo.isEsDiplomatico());
        dto.setPlazoMaximoDias(vehiculo.getPlazoMaximoDias());
        dto.setRutPasajero(vehiculo.getPasajero().getRut()); // Extraemos el RUT de la entidad relacionada
        return dto;
    }
}
