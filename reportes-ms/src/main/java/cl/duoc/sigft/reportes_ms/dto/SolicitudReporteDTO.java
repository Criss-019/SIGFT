package cl.duoc.sigft.reportes_ms.dto;

import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SolicitudReporteDTO {

    @NotNull(message = "El tipo de reporte es obligatorio")
    private TipoReporte tipoReporte;

    @NotNull(message = "El formato es obligatorio")
    private FormatoReporte formato;

    @NotNull(message = "La fecha desde es obligatoria")
    private LocalDate fechaDesde;

    @NotNull(message = "La fecha hasta es obligatoria")
    private LocalDate fechaHasta;

    @NotBlank(message = "El solicitante es obligatorio")
    private String solicitante;

    private String observaciones;
}
