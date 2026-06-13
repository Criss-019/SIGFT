package cl.duoc.sigft.pasajeros_ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.sigft.pasajeros_ms.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    List<Vehiculo> findByPasajeroRut(String rut);
}
