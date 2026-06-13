package cl.duoc.sigft.pasajeros_ms.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PasajeroDTO {
    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 0, message = "La edad no puede ser negativa")
    private int edad;

    @NotBlank(message = "La nacionalidad es obligatoria")
    private String nacionalidad;

    // Solo para visualización en GET, al hacer POST se ignora si va vacío
    private List<VehiculoDTO> vehiculos;
}
