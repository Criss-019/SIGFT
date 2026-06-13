package cl.duoc.sigft.tramites_ms.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TramiteFronterizoDTO {
    @NotBlank(message = "El ID del trámite es obligatorio")
    private String idTramite;
    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;
    private String estadoTramite;
    @NotBlank(message = "Aduana de origen obligatoria")
    private String aduanaOrigen;
    @NotBlank(message = "Aduana de destino obligatoria")
    private String aduanaDestino;
    @NotEmpty(message = "Debe registrar al menos un pasajero")
    private List<String> rutsPasajeros;
    private List<String> patentesVehiculos; // Opcional (0..*)

}
