package cl.duoc.sigft.integracion_ms.dto;

import cl.duoc.sigft.integracion_ms.model.EstadoIntegracion;
import cl.duoc.sigft.integracion_ms.model.TipoOperacion;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RegistroIntegracionDTO {

    private Long id;

    @NotBlank(message = "El id de trámite de referencia es obligatorio")
    @Size(max = 50, message = "El idTramiteRef no puede superar 50 caracteres")
    private String idTramiteRef;

    @NotBlank(message = "El RUT del pasajero es obligatorio")
    @Size(max = 12, message = "El RUT no puede superar 12 caracteres")
    private String rutPasajero;

    @NotNull(message = "El tipo de operación es obligatorio")
    private TipoOperacion tipoOperacion;

    private EstadoIntegracion estado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaRespuesta;
    private String codigoRespuestaArgentina;

    @Size(max = 500, message = "El mensaje no puede superar 500 caracteres")
    private String mensajeRespuesta;

    private String datosEnviados;
    private Integer intentosEnvio;
}
