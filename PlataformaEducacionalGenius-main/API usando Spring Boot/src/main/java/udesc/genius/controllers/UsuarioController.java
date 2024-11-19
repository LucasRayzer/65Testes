package udesc.genius.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import jakarta.validation.Valid;

import udesc.genius.dtos.UsuarioRecordDto;
import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    //Criar usuario
    @PostMapping("/usuarios")
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid UsuarioRecordDto UsuarioRecordDto) {
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(UsuarioRecordDto, usuarioModel);
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        for (UsuarioModel u: usuarios) {
            if (usuarioModel.getLogin().equalsIgnoreCase(u.getLogin())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("login já cadastrado");
            }
            if (usuarioModel.getEmail().equalsIgnoreCase(u.getEmail())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email já cadastrado");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuarioModel));
    }

    //Retornar todos os usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioModel>> getTodosUsuarios() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findAll());
    }

    //Retornar usuario especifico
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable(value="id") UUID id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
    }

    //Atualizar usuario
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid UsuarioRecordDto usuarioRecordDto) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        var usuarioModel = usuario.get();
        BeanUtils.copyProperties(usuarioRecordDto, usuarioModel);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioModel));
    }

    //Excluir usuario e suas relações com cursos
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value="id") UUID id) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }
        for (CursoModel c : usuario.get().getCursos()) {
            c.getUsuarios().remove(usuario.get());
            cursoRepository.save(c);
        }
        usuario.get().getCursos().clear();
        usuarioRepository.save(usuario.get());
        usuarioRepository.delete(usuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
    }

    @GetMapping("/usuarios/{id}/disciplinas")
    public ResponseEntity<Object> getAllDisciplinasByProfessor(@PathVariable(value="id") UUID idProfessor) {
        Optional<UsuarioModel> usuario = usuarioRepository.findById(idProfessor);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("professor não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario.get().getDisciplinas());
    }
    
}