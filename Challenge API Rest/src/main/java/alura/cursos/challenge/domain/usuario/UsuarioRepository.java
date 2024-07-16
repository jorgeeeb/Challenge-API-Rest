package alura.cursos.challenge.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.JavaBean;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByIdAndActivoTrue(Long id);
    UserDetails findByEmail(String username);
    boolean existsByEmail(String email);

}
