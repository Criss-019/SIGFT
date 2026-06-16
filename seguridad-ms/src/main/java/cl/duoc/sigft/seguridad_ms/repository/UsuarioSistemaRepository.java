package cl.duoc.sigft.seguridad_ms.repository;

import cl.duoc.sigft.seguridad_ms.model.RolUsuario;
import cl.duoc.sigft.seguridad_ms.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
    Optional<UsuarioSistema> findByUsername(String username);
    Optional<UsuarioSistema> findByEmail(String email);
    List<UsuarioSistema> findByRol(RolUsuario rol);
    List<UsuarioSistema> findByActivoTrue();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
