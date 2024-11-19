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

import udesc.genius.dtos.SecaoRecordDto;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.SecaoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.SecaoRepository;

@RestController
public class SecaoController {
    @Autowired
    SecaoRepository secaoRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;
    
    //cria e adiciona uma seção a uma disciplina
    @PostMapping("/disciplina/{id}/secoes")
    public ResponseEntity<Object> salvarSecao(@PathVariable(value="id") UUID idDisciplina,
                                                    @RequestBody @Valid SecaoRecordDto secaoRecordDto) {
        var secaoModel = new SecaoModel();
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(idDisciplina);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada.");
        }
        BeanUtils.copyProperties(secaoRecordDto, secaoModel);
        secaoModel.setDisciplina(disciplina.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(secaoRepository.save(secaoModel));
    }

    //Retorna as seções de uma disciplina
    @GetMapping("disciplina/{id}/secoes")
    public ResponseEntity<Object> getTodasSecoes(@PathVariable(value="id") UUID idDisciplina) {
        Optional<DisciplinaModel> disciplina = disciplinaRepository.findById(idDisciplina);
        if (disciplina.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Disciplina não encontrada.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(disciplina.get().getSecoes());
    }

    //Retorna uma seção em específico
    @GetMapping("/secoes/{id}")
    public ResponseEntity<Object> getSecao(@PathVariable(value="id") UUID id) {
        Optional<SecaoModel> secao = secaoRepository.findById(id);
        if (secao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seção não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(secao.get());
    }

    //Altera uma seção em especifico
    @PutMapping("/secoes/{id}")
    public ResponseEntity<Object> atualizarSecao(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid SecaoRecordDto secaoRecordDto) {
        Optional<SecaoModel> secao = secaoRepository.findById(id);
        if (secao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seção não encontrada");
        }
        var secaoModel = secao.get();
        BeanUtils.copyProperties(secaoRecordDto, secaoModel);
        return ResponseEntity.status(HttpStatus.OK).body(secaoRepository.save(secaoModel));
    }

    //deleta uma seção em especifico
    @DeleteMapping("/secoes/{id}")
    public ResponseEntity<Object> deletarSecao(@PathVariable(value="id") UUID id) {
        Optional<SecaoModel> secao = secaoRepository.findById(id);
        if (secao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seção não encontrada");
        }
        secaoRepository.delete(secao.get());
        return ResponseEntity.status(HttpStatus.OK).body("Seção deletada com sucesso");
    }
}
