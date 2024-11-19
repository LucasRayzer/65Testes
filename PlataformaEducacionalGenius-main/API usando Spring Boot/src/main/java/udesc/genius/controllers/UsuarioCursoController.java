package udesc.genius.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

@RestController
public class UsuarioCursoController {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    CursoRepository cursoRepository;

    //Retorna todos os cursos atrelados ao usuario
    @GetMapping("/usuarios/{id}/cursos")
    public ResponseEntity<Object> getCursosUsuario(@PathVariable(value="id") UUID idUsuario) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } 
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get().getCursos());
    }

    //Adicionar Curso a usuario
    @PutMapping("/usuarios/{idUsuario}/cursos/{idCurso}")
    public ResponseEntity<Object> adicionarCurso(@PathVariable(value="idUsuario") UUID idUsuario, @PathVariable(value="idCurso") UUID idCurso) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        Optional<CursoModel> curso = cursoRepository.findById(idCurso);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        usuario.get().getCursos().add(curso.get());
        usuarioRepository.save(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Curso adicionado com sucesso");
    }
}
