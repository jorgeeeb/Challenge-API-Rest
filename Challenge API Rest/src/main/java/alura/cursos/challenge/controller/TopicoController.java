package alura.cursos.challenge.controller;

import alura.cursos.challenge.domain.topico.*;
import alura.cursos.challenge.domain.usuario.DatosRespuestaUsuario;
import alura.cursos.challenge.domain.usuario.DatosUsuario;
import alura.cursos.challenge.domain.usuario.Usuario;
import alura.cursos.challenge.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> registroTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Usuario autor = usuarioRepository.findById(datos.autor().id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si ya existe un tópico con el mismo título
        if (topicoRepository.existsByTitulo(datos.titulo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un tópico con el mismo título");
        }

        // Verificar si ya existe un tópico con el mismo mensaje
        if (topicoRepository.existsByMensaje(datos.mensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un tópico con el mismo mensaje");
        }

        Topico topico = new Topico(datos);
        topico.setAutor(autor);
        topicoRepository.save(topico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        DatosRespuestaUsuario autorSinContraseña = new DatosRespuestaUsuario(
                autor.getId(),
                autor.getNombre(),
                autor.getEmail()
        );
        DatosRegistroTopico datos2 = new DatosRegistroTopico(
                datos.titulo(),
                datos.mensaje(),
                autorSinContraseña,
                datos.nombreCurso());

        return ResponseEntity.created(url).body(datos2);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopico(@PathVariable Long id) {
        Optional<Topico> topicoOpt = topicoRepository.findByIdAndActivoTrue(id);
        if (topicoOpt.isPresent()) {
            return ResponseEntity.ok(new DatosRespuestaTopico(
                    topicoOpt.get().getId(), topicoOpt.get().getTitulo(),
                    topicoOpt.get().getMensaje(), topicoOpt.get().getNombreCurso()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tópico no encontrado o está inactivo");
        }
    }





    @GetMapping
    public ResponseEntity<List<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 15) Pageable paginacion) {
        Page<Topico> topicos = topicoRepository.findByActivoTrue(paginacion);
        List<DatosListadoTopico> contenido = topicos.stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(contenido);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        DatosRespuestaTopico datosRespuesta = new DatosRespuestaTopico(
                topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getNombreCurso()
        );
        return ResponseEntity.ok(datosRespuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }
}
