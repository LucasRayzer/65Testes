
package udesc.genius.Integração;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import udesc.genius.controllers.DisciplinaController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.DisciplinaRepository;


@SpringBootTest
class DisciplinaCursoControllerTestInteg {

    @Autowired
    private DisciplinaController disciplinaController;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private UUID cursoId;

    @BeforeEach
    void setUp() {
        CursoModel curso = new CursoModel();
        curso.setTitulo("Engenharia de Software");
        curso.setDescricao("Curso de Engenharia de Software");
        curso = cursoRepository.save(curso);
        cursoId = curso.getIdCurso();
    }

    @Test
    void testAdicionarDisciplinaAoCurso() {
        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setTitulo("Testes");
        disciplina.setCurso(cursoRepository.findById(cursoId).get());  
        disciplina = disciplinaRepository.save(disciplina);

        assertNotNull(disciplina.getIdDisciplina(), "A disciplina não foi criada corretamente.");
        assertEquals(cursoId, disciplina.getCurso().getIdCurso(), "A disciplina não está associada ao curso corretamente.");
    }
}
