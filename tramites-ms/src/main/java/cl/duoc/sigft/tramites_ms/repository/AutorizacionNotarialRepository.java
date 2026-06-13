package cl.duoc.sigft.tramites_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.sigft.tramites_ms.model.AutorizacionNotarial;

@Repository
public interface AutorizacionNotarialRepository extends JpaRepository<AutorizacionNotarial, String>{

}
