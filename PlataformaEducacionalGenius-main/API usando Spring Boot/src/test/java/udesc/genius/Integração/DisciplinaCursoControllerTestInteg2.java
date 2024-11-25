package udesc.genius.Integração;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import udesc.genius.controllers.DisciplinaController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.DisciplinaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DisciplinaCursoControllerTestInteg2 {

    @Autowired
    private DisciplinaController disciplinaController;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private UUID idCurso;
    private UUID idDisciplina;

    @BeforeEach
    void setUp() {
        CursoModel curso = new CursoModel();
        curso.setTitulo("Engenharia de Software");
        curso = cursoRepository.save(curso);
        idCurso = curso.getIdCurso();

        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setTitulo("Desenvolvimento de Software");
        disciplina.setCurso(curso);
        disciplina = disciplinaRepository.save(disciplina);
        idDisciplina = disciplina.getIdDisciplina();
    }

    @Test
    void testDeletarDisciplinaDeCurso() {
      
    }
}
