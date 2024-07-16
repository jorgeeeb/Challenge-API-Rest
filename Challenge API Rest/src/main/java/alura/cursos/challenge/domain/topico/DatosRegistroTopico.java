package alura.cursos.challenge.domain.topico;

import alura.cursos.challenge.domain.curso.NombreCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        alura.cursos.challenge.domain.usuario.DatosRespuestaUsuario autor,
        @NotNull NombreCurso nombreCurso

) {
}
