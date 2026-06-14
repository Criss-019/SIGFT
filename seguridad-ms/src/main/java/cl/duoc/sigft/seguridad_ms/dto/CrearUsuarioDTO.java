package cl.duoc.sigft.seguridad_ms.dto;

import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CrearUsuarioDTO {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 60, message = "El username debe tener entre 4 y 60 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;
}
