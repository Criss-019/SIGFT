package cl.duoc.sigft.tramites_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.sigft.tramites_ms.model.DeclaracionJuradaSAG;

import java.util.List;

@Repository
public interface DeclaracionJuradaSAGRepository extends JpaRepository<DeclaracionJuradaSAG, String> {
    List<DeclaracionJuradaSAG> findByRutPasajeroOrderByFechaRegistroDesc(String rutPasajero);
}
