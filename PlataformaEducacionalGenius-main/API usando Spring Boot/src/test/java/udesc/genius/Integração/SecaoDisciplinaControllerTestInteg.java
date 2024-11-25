package udesc.genius.Integração;

import jakarta.transaction.Transactional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import udesc.genius.controllers.SecaoController;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.SecaoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.SecaoRepository;

@SpringBootTest
class SecaoDisciplinaControllerTestInteg {

    @Autowired
    private SecaoController secaoController;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private SecaoRepository secaoRepository;

    private UUID disciplinaId;
    private UUID secaoId;

    @BeforeEach
    void setUp() {
        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setTitulo("Engenharia de Software");
        disciplina.setDescricao("Disciplina de Engenharia de Software");
        disciplina = disciplinaRepository.save(disciplina);
        disciplinaId = disciplina.getIdDisciplina();

        SecaoModel secao = new SecaoModel();
        secao.setTitulo("A");
        secao.setDisciplina(disciplina);
        secao = secaoRepository.save(secao);
        secaoId = secao.getIdSecao();
    }

    @Test
    @Transactional
    void testRemoverSecaoDaDisciplina() {
        ResponseEntity<Object> response = secaoController.deletarSecao(secaoId);

        assertFalse(secaoRepository.existsById(secaoId), "A seção não foi removida corretamente.");

        DisciplinaModel disciplina = disciplinaRepository.findById(disciplinaId).get();
        
        boolean secaoRemovida = disciplina.getSecoes().stream()
                .noneMatch(secao -> secao.getIdSecao().equals(secaoId));
        
        assertTrue(secaoRemovida, "A seção não foi removida da disciplina corretamente.");
    }
}
