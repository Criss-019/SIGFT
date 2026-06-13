package cl.duoc.sigft.pasajeros_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.sigft.pasajeros_ms.model.Pasajero;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, String> {

}
