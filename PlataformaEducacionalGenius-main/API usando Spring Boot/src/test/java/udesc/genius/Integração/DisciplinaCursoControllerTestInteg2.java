package udesc.genius.Integração;

import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.DisciplinaRepository;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)  
@AutoConfigureMockMvc
public class DisciplinaCursoControllerTestInteg2 {

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
        // Criando o curso
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
    @Transactional
    void testDeletarDisciplinaDeCurso() {
    
    }

}
