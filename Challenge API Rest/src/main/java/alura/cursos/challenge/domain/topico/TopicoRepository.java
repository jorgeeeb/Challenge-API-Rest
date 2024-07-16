package alura.cursos.challenge.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findByActivoTrue(Pageable paginacion);
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    Optional<Topico> findByIdAndActivoTrue(Long id);

    boolean existsByMensaje(String mensaje);

    boolean existsByTitulo(String titulo);
}
