package cl.duoc.sigft.pasajeros_ms.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VehiculoDTO {
    @NotBlank(message = "La patente es obligatoria")
    private String patente;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;

    @NotNull(message = "Debe indicar si es diplomático")
    private Boolean esDiplomatico;

    @Min(value = 1, message = "El plazo debe ser mayor a 0")
    private Integer plazoMaximoDias;

    @NotBlank(message = "El RUT del pasajero es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    private String rutPasajero;
}
