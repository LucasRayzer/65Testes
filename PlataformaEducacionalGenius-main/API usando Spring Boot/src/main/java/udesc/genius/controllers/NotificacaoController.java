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

import udesc.genius.dtos.NotificacaoRecordDto;
import udesc.genius.models.NotificacaoModel;
import udesc.genius.repositories.NotificacaoRepository;

@RestController
public class NotificacaoController {
    @Autowired
    NotificacaoRepository notificacaoRepository;
    
    @PostMapping("/notificacoes")
    public ResponseEntity<NotificacaoModel> salvarNotificacao(@RequestBody @Valid NotificacaoRecordDto notificacaoRecordDto) {
        var notificacaoModel = new NotificacaoModel();
        BeanUtils.copyProperties(notificacaoRecordDto, notificacaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacaoRepository.save(notificacaoModel));
    }

    @GetMapping("/notificacoes")
    public ResponseEntity<List<NotificacaoModel>> getTodasNotificacoes() {
        return ResponseEntity.status(HttpStatus.OK).body(notificacaoRepository.findAll());
    }

    @GetMapping("/notificacoes/{id}")
    public ResponseEntity<Object> getNotificacao(@PathVariable(value="id") UUID id) {
        Optional<NotificacaoModel> notificacao = notificacaoRepository.findById(id);
        if (notificacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificação não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(notificacao.get());
    }

    @PutMapping("/notificacoes/{id}")
    public ResponseEntity<Object> atualizarNotificacao(@PathVariable(value="id") UUID id,
                                                    @RequestBody @Valid NotificacaoRecordDto notificacaoRecordDto) {
        Optional<NotificacaoModel> notificacao = notificacaoRepository.findById(id);
        if (notificacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificação não encontrada");
        }
        var notificacaoModel = notificacao.get();
        BeanUtils.copyProperties(notificacaoRecordDto, notificacaoModel);
        return ResponseEntity.status(HttpStatus.OK).body(notificacaoRepository.save(notificacaoModel));
    }

    @DeleteMapping("/notificacoes/{id}")
    public ResponseEntity<Object> deletarNotificacao(@PathVariable(value="id") UUID id) {
        Optional<NotificacaoModel> notificacao = notificacaoRepository.findById(id);
        if (notificacao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificacao não encontrada");
        }
        notificacaoRepository.delete(notificacao.get());
        return ResponseEntity.status(HttpStatus.OK).body("Notificacao deletada com sucesso");
    }
}
