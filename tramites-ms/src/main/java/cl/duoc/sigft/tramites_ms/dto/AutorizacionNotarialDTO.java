package cl.duoc.sigft.tramites_ms.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AutorizacionNotarialDTO {
    @NotBlank(message = "El ID de la autorización es obligatorio")
    private String idAutorizacion;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private Date fechaEmision;

    @NotBlank(message = "La notaría de origen es obligatoria")
    private String notariaOrigen;

    private String adjuntoPDF;

    @NotBlank(message = "El RUT del pasajero es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    private String rutPasajero;
}
