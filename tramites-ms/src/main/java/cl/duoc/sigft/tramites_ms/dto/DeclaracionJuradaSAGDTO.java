package cl.duoc.sigft.tramites_ms.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DeclaracionJuradaSAGDTO {
    @NotBlank(message = "El ID de la declaración es obligatorio")
    private String idDeclaracion;

    @NotNull(message = "La fecha de registro es obligatoria")
    private Date fechaRegistro;

    private boolean traeProductosAnimales;
    private boolean traeProductosVegetales;
    private boolean poseeMascotas;
    private boolean requiereRevisionSAG;

    @NotBlank(message = "El RUT del pasajero es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    private String rutPasajero;

}
