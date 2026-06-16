package cl.duoc.sigft.reportes_ms.repository;

import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.Reporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipoReporte(TipoReporte tipoReporte);
    List<Reporte> findByFormato(FormatoReporte formato);
    List<Reporte> findByGeneradoPor(String generadoPor);
    List<Reporte> findByFechaDesdeBetween(LocalDate inicio, LocalDate fin);
}
