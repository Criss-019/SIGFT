package cl.duoc.sigft.integracion_ms.dto;

import cl.duoc.sigft.integracion_ms.model.TipoOperacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SolicitudIntegracionDTO {

    @NotBlank(message = "El id de trámite de referencia es obligatorio")
    private String idTramiteRef;

    @NotBlank(message = "El RUT del pasajero es obligatorio")
    private String rutPasajero;

    @NotNull(message = "El tipo de operación es obligatorio")
    private TipoOperacion tipoOperacion;

    private String datosAdicionales;
}
