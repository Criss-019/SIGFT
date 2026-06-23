package cl.duoc.sigft.pasajeros_ms.service.impl;

import cl.duoc.sigft.pasajeros_ms.dto.PasajeroDTO;
import cl.duoc.sigft.pasajeros_ms.dto.VehiculoDTO;
import cl.duoc.sigft.pasajeros_ms.model.Pasajero;
import cl.duoc.sigft.pasajeros_ms.repository.PasajeroRepository;
import cl.duoc.sigft.pasajeros_ms.service.PasajeroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasajeroServiceImpl implements PasajeroService {
    
    private final PasajeroRepository repository;

    @Override
    @Transactional
    public PasajeroDTO registrar(PasajeroDTO dto) {
        log.info("Capa Service - Registrando pasajero RUT: {}", dto.getRut());
        
        if (repository.existsById(dto.getRut())) {
            throw new IllegalArgumentException("El pasajero ya existe");
        }

        Pasajero pasajero = Pasajero.builder()
                .rut(dto.getRut())
                .nombre(dto.getNombre())
                .edad(dto.getEdad())
                .nacionalidad(dto.getNacionalidad())
                .email(dto.getEmail())
                .build();

        // IMPLEMENTACIÓN DE LA LÓGICA DEL DIAGRAMA
        if (!pasajero.verificarEdad()) {
            log.warn("Capa Service - Pasajero es menor de edad");
            throw new IllegalArgumentException("El pasajero debe ser mayor de edad (18+) para registrarse individualmente");
        }

        Pasajero guardado = repository.save(pasajero);
        return mapearADto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PasajeroDTO obtenerPorRut(String rut) {
        Pasajero pasajero = repository.findById(rut)
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado"));
        return mapearADto(pasajero);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PasajeroDTO> obtenerTodos() {
        return repository.findAll().stream().map(this::mapearADto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PasajeroDTO actualizar(String rut, PasajeroDTO dto) {
        Pasajero pasajero = repository.findById(rut)
                .orElseThrow(() -> new RuntimeException("Pasajero no encontrado para actualizar"));

        pasajero.setNombre(dto.getNombre());
        pasajero.setEdad(dto.getEdad());
        pasajero.setNacionalidad(dto.getNacionalidad());
        pasajero.setEmail(dto.getEmail());

        repository.save(pasajero);
        return mapearADto(pasajero);
    }

    @Override
    @Transactional
    public void eliminar(String rut) {
        if (!repository.existsById(rut)) {
            throw new RuntimeException("Pasajero no encontrado para eliminar");
        }
        repository.deleteById(rut);
    }

    // MAPEO ACTUALIZADO PARA INCLUIR LA RELACIÓN @OneToMany
    private PasajeroDTO mapearADto(Pasajero pasajero) {
        PasajeroDTO dto = new PasajeroDTO();
        dto.setRut(pasajero.getRut());
        dto.setNombre(pasajero.getNombre());
        dto.setEdad(pasajero.getEdad());
        dto.setNacionalidad(pasajero.getNacionalidad());
        dto.setEmail(pasajero.getEmail());
        
        // Si el pasajero tiene vehículos, los mapeamos también
        if (pasajero.getVehiculos() != null && !pasajero.getVehiculos().isEmpty()) {
            List<VehiculoDTO> vehiculosDto = pasajero.getVehiculos().stream().map(v -> {
                VehiculoDTO vDto = new VehiculoDTO();
                vDto.setPatente(v.getPatente());
                vDto.setTipoVehiculo(v.getTipoVehiculo());
                vDto.setEsDiplomatico(v.isEsDiplomatico());
                vDto.setPlazoMaximoDias(v.getPlazoMaximoDias());
                vDto.setRutPasajero(pasajero.getRut());
                return vDto;
            }).collect(Collectors.toList());
            dto.setVehiculos(vehiculosDto);
        }
        return dto;
    }
}
