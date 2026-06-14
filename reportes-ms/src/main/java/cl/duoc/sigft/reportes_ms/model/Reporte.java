package cl.duoc.sigft.reportes_ms.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reporte", nullable = false, length = 30)
    private TipoReporte tipoReporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "formato", nullable = false, length = 10)
    private FormatoReporte formato;

    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    @Column(name = "total_registros")
    private Integer totalRegistros;

    @Column(name = "nombre_archivo", length = 200)
    private String nombreArchivo;

    @Column(name = "generado_por", nullable = false, length = 60)
    private String generadoPor;

    @Column(name = "fecha_generacion", nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    // Lógica de dominio: valida que el rango de fechas sea válido
    public boolean esRangoFechasValido() {
        return !this.fechaDesde.isAfter(this.fechaHasta);
    }
}
