package cl.duoc.sigft.reportes_ms.dto;

import cl.duoc.sigft.reportes_ms.model.FormatoReporte;
import cl.duoc.sigft.reportes_ms.model.TipoReporte;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ReporteDTO {

    private Long id;

    @NotNull(message = "El tipo de reporte es obligatorio")
    private TipoReporte tipoReporte;

    @NotNull(message = "El formato es obligatorio")
    private FormatoReporte formato;

    @NotNull(message = "La fecha desde es obligatoria")
    private LocalDate fechaDesde;

    @NotNull(message = "La fecha hasta es obligatoria")
    private LocalDate fechaHasta;

    private Integer totalRegistros;
    private String nombreArchivo;

    @NotBlank(message = "El campo generadoPor es obligatorio")
    @Size(max = 60, message = "El campo generadoPor no puede superar 60 caracteres")
    private String generadoPor;

    private LocalDateTime fechaGeneracion;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;
}
