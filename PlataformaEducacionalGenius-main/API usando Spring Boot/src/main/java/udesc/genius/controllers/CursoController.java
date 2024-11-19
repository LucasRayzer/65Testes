package udesc.genius.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import udesc.genius.dtos.CursoRecordDto;
import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

@RestController
public class CursoController {
    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    //cria um curso
    @PostMapping("/cursos")
    public ResponseEntity<Object> salvarCurso(@RequestBody @Valid CursoRecordDto cursoRecordDto) {
        var cursoModel = new CursoModel();
        BeanUtils.copyProperties(cursoRecordDto, cursoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoRepository.save(cursoModel));
    }

    //retorna todos os cursos
    @GetMapping("/cursos")
    public ResponseEntity<List<CursoModel>> getTodosCursos() {
        return ResponseEntity.status(HttpStatus.OK).body(cursoRepository.findAll());
    }

    //retorna um curso em especifico
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Object> getcurso(@PathVariable(value="id") UUID id) {
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(curso.get());
    }

    //Edita um curso em especifico
    @PutMapping("/cursos/{id}")
    public ResponseEntity<Object> atualizarCurso(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid CursoRecordDto cursoRecordDto) {
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        var cursoModel = curso.get();
        BeanUtils.copyProperties(cursoRecordDto, cursoModel);
        return ResponseEntity.status(HttpStatus.OK).body(cursoRepository.save(cursoModel));
    }

    //Deleta o curso e remove seus relacionamentos com usuarios
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<Object> deletarCurso(@PathVariable(value="id") UUID id) {
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        curso.get().getUsuarios();
        for (UsuarioModel u : curso.get().getUsuarios()) {
            u.getCursos().remove(curso.get());
            usuarioRepository.save(u);
        }
        curso.get().getUsuarios().clear();
        cursoRepository.save(curso.get());
        cursoRepository.delete(curso.get());
        return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso.");
    }
}