package alura.cursos.challenge.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario registrarUsuario(DatosUsuario datosRegistroUsuario) {
        if (existeEmail(datosRegistroUsuario.email())) {
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario = new Usuario(datosRegistroUsuario);
        return usuarioRepository.save(usuario);
    }
}
