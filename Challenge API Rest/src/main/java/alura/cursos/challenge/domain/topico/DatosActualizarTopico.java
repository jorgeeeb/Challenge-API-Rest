package alura.cursos.challenge.domain.topico;

import alura.cursos.challenge.domain.curso.NombreCurso;

public record DatosActualizarTopico(
        Long id,
        String titulo,
        String mensaje,
        NombreCurso curso

) {
}
