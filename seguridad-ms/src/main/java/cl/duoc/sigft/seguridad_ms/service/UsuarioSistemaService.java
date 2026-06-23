package cl.duoc.sigft.seguridad_ms.service;

import cl.duoc.sigft.seguridad_ms.dto.CrearUsuarioDTO;
import cl.duoc.sigft.seguridad_ms.dto.LoginRequestDTO;
import cl.duoc.sigft.seguridad_ms.dto.LoginResponseDTO;
import cl.duoc.sigft.seguridad_ms.dto.UsuarioSistemaDTO;
import cl.duoc.sigft.seguridad_ms.model.RolUsuario;

import java.util.List;

public interface UsuarioSistemaService {
    UsuarioSistemaDTO crearUsuario(CrearUsuarioDTO dto);
    UsuarioSistemaDTO obtenerPorId(Long id);
    UsuarioSistemaDTO obtenerPorUsername(String username);
    List<UsuarioSistemaDTO> obtenerTodos();
    List<UsuarioSistemaDTO> obtenerPorRol(RolUsuario rol);
    List<UsuarioSistemaDTO> obtenerActivos();
    UsuarioSistemaDTO actualizar(Long id, UsuarioSistemaDTO dto);
    void desactivar(Long id);
    void eliminar(Long id);
    LoginResponseDTO autenticar(LoginRequestDTO loginRequest); // ← devuelve token
}
