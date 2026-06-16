package cl.duoc.sigft.integracion_ms.repository;

import cl.duoc.sigft.integracion_ms.model.EstadoIntegracion;
import cl.duoc.sigft.integracion_ms.model.RegistroIntegracion;
import cl.duoc.sigft.integracion_ms.model.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroIntegracionRepository extends JpaRepository<RegistroIntegracion, Long> {
    List<RegistroIntegracion> findByRutPasajero(String rutPasajero);
    List<RegistroIntegracion> findByEstado(EstadoIntegracion estado);
    List<RegistroIntegracion> findByTipoOperacion(TipoOperacion tipoOperacion);
    List<RegistroIntegracion> findByIdTramiteRef(String idTramiteRef);
    List<RegistroIntegracion> findByEstadoAndIntentosEnvioLessThan(EstadoIntegracion estado, int maxIntentos);
}
