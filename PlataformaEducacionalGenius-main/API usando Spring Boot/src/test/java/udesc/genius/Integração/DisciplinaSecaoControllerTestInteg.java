
package udesc.genius.Integração;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import udesc.genius.controllers.DisciplinaController;
import udesc.genius.controllers.SecaoController;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.SecaoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.SecaoRepository;

@SpringBootTest
class DisciplinaSecaoControllerTestInteg {

    @Autowired
    private DisciplinaController disciplinaController;

    @Autowired
    private SecaoController secaoController;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private SecaoRepository secaoRepository;

    private UUID disciplinaId;

    @BeforeEach
    void setUp() {
        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setTitulo("Engenharia de Software");
        disciplina = disciplinaRepository.save(disciplina);
        disciplinaId = disciplina.getIdDisciplina();
    }

    @Test
    void testCriarSecaoNaDisciplina() {
        SecaoModel secao = new SecaoModel();
        secao.setTitulo("A");
        secao.setDisciplina(disciplinaRepository.findById(disciplinaId).get());
        secao = secaoRepository.save(secao);

        assertNotNull(secao.getIdSecao(), "A seção não foi criada corretamente.");
        assertEquals(disciplinaId, secao.getDisciplina().getIdDisciplina(), "A seção não está associada à disciplina corretamente.");
    }
}
