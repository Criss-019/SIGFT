package cl.duoc.sigft.seguridad_ms.dto;

import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioSistemaDTO {

    private Long id;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 4, max = 60, message = "El username debe tener entre 4 y 60 caracteres")
    private String username;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombreCompleto;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener formato válido")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private RolUsuario rol;

    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
}
