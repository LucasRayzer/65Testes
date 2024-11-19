package udesc.genius.controllers;

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

import udesc.genius.dtos.DisciplinaRecordDto;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.models.CursoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.UsuarioRepository;

@RestController
public class DisciplinaController {
    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    
    //cria disciplina e adiciona ao curso
    @PostMapping("/cursos/{id}/disciplinas")
    public ResponseEntity<Object> salvarDisciplina(@PathVariable(value="id") UUID idCurso, @RequestBody @Valid DisciplinaRecordDto disciplinaRecordDto) {
        var disciplinaModel = new DisciplinaModel();
        Optional<CursoModel> curso = cursoRepository.findById(idCurso);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        BeanUtils.copyProperties(disciplinaRecordDto, disciplinaModel);
        disciplinaModel.setCurso(curso.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaRepository.save(disciplinaModel));
    }

    //retorna todas as disciplinas de um curso
    @GetMapping("/cursos/{id}/disciplinas")
    public ResponseEntity<Object> getTodasDisciplinas(@PathVariable(value="id") UUID idCurso) {
        Optional<CursoModel> curso = cursoRepository.findById(idCurso);
        if (curso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(curso.get().getDisciplinas());
    }

    //retorna uma disciplina em especifico
    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<Object> getDisciplina(@PathVariable(value="id") UUID idDisciplina) {
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(idDisciplina);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(disciplina.get());
    }

    //edita uma disciplina em especifico
    @PutMapping("/disciplinas/{id}")
    public ResponseEntity<Object> atualizarDisciplina(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid DisciplinaRecordDto disciplinaRecordDto) {
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(id);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada");
        }
        var disciplinaModel = disciplina.get();
        BeanUtils.copyProperties(disciplinaRecordDto, disciplinaModel);
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.save(disciplinaModel));
    }

    @PutMapping("/disciplinas/{idDisciplina}/professor/{idProfessor}")
    public ResponseEntity<Object> atrelarProfessor(@PathVariable(value="idDisciplina") UUID idDisciplina,
                                                    @PathVariable(value="idProfessor") UUID idProfessor) {
        Optional<UsuarioModel> professor = usuarioRepository.findById(idProfessor);
        if (professor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encotrado");
        }
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(idDisciplina);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada");
        }
        disciplina.get().setProfessor(professor.get());
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.save(disciplina.get()));   
    }

    //Remove a disciplina do professor
    @PutMapping("/professor/{idProfessor}/disciplinas/{idDisciplina}")
    public ResponseEntity<Object> desatrelarProfessor(@PathVariable(value="idDisciplina") UUID idDisciplina,
                                                    @PathVariable(value="idProfessor") UUID idProfessor) {
        Optional<UsuarioModel> professor = usuarioRepository.findById(idProfessor);
        if (professor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado.");
        }
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(idDisciplina);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada.");
        }
        professor.get().getDisciplinas().remove(disciplina.get());
        usuarioRepository.save(professor.get());
        disciplina.get().setProfessor(null);
        return ResponseEntity.status(HttpStatus.OK).body(disciplinaRepository.save(disciplina.get()));   
    }

    //Excluir disciplina
    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Object> deletarDisciplina(@PathVariable(value="id") UUID id) {
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(id);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada.");
        }
        disciplinaRepository.delete(disciplina.get());
        return ResponseEntity.status(HttpStatus.OK).body("Disciplina excluída com sucesso.");
    }
}