package cl.duoc.sigft.seguridad_ms.dto;

import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;        // Bearer JWT
    private String username;
    private String nombreCompleto;
    private RolUsuario rol;
    private long expiresIn;      // milisegundos hasta expiración
    //                           (útil para el frontend)
}
