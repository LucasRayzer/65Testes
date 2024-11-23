package udesc.genius.Unitarios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.DisciplinaController;
import udesc.genius.dtos.DisciplinaRecordDto;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.UsuarioRepository;

import java.util.Optional;
import java.util.UUID;

public class DisciplinaControllerTests {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DisciplinaController disciplinaController;

    private CursoModel cursoModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cursoModel = new CursoModel();
        cursoModel.setIdCurso(UUID.randomUUID());
        cursoModel.setTitulo("Engenharia de Software");
        cursoModel.setDescricao("Curso de graduação");
    }
    //CT14 passa
    @Test
    public void testSalvarDisciplinaComSucesso() {

        UUID idCurso = cursoModel.getIdCurso();
        DisciplinaRecordDto disciplinaDto = new DisciplinaRecordDto("Algoritmos", "Introdução a algoritmos");

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoModel));

        DisciplinaModel disciplinaModel = new DisciplinaModel();
        disciplinaModel.setIdDisciplina(UUID.randomUUID());
        disciplinaModel.setTitulo(disciplinaDto.titulo());
        disciplinaModel.setDescricao(disciplinaDto.descricao());
        disciplinaModel.setCurso(cursoModel);

        when(disciplinaRepository.save(any(DisciplinaModel.class))).thenReturn(disciplinaModel);

        ResponseEntity<Object> response = disciplinaController.salvarDisciplina(idCurso, disciplinaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof DisciplinaModel);

        DisciplinaModel resultado = (DisciplinaModel) response.getBody();
        assertEquals(disciplinaDto.titulo(), resultado.getTitulo());
        assertEquals(disciplinaDto.descricao(), resultado.getDescricao());
        assertEquals(cursoModel, resultado.getCurso());

        verify(cursoRepository, times(1)).findById(idCurso);
        verify(disciplinaRepository, times(1)).save(any(DisciplinaModel.class));
    }
    //CT15 passa pois testo se ele não salva e realmente não salva
    @Test
    public void testSalvarDisciplinaComCursoInexistente() {
        UUID idCursoInvalido = UUID.randomUUID(); 
        DisciplinaRecordDto disciplinaDto = new DisciplinaRecordDto("Algoritmos", "Introdução a algoritmos");
      
        when(cursoRepository.findById(idCursoInvalido)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = disciplinaController.salvarDisciplina(idCursoInvalido, disciplinaDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Curso não encontrado.", response.getBody());

        //cursoRepository foi consultado, mas disciplinaRepository não foi chamado
        verify(cursoRepository, times(1)).findById(idCursoInvalido);
        verify(disciplinaRepository, never()).save(any(DisciplinaModel.class));
    }
    // CT16 passa
    @Test
    public void testAlocarProfessorEmDisciplinaExistente() {
       
        UsuarioModel professor = new UsuarioModel();
        professor.setIdUsuario(UUID.randomUUID());
        professor.setNome("Marcelo de Souza");

        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setIdDisciplina(UUID.randomUUID());
        disciplina.setTitulo("Algoritmos");
        disciplina.setDescricao("Introdução a Algoritmos");

        UUID idDisciplina = disciplina.getIdDisciplina();
        UUID idProfessor = professor.getIdUsuario();

        when(usuarioRepository.findById(idProfessor)).thenReturn(Optional.of(professor));
        when(disciplinaRepository.findById(idDisciplina)).thenReturn(Optional.of(disciplina));

        disciplina.setProfessor(professor);
        when(disciplinaRepository.save(any(DisciplinaModel.class))).thenReturn(disciplina);

        ResponseEntity<Object> response = disciplinaController.atrelarProfessor(idDisciplina, idProfessor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof DisciplinaModel);

        DisciplinaModel resultado = (DisciplinaModel) response.getBody();
        assertEquals(professor, resultado.getProfessor());

        verify(usuarioRepository, times(1)).findById(idProfessor);
        verify(disciplinaRepository, times(1)).findById(idDisciplina);
        verify(disciplinaRepository, times(1)).save(any(DisciplinaModel.class));
    }
    //CT17
    @Test
    public void testAlocarProfessorInexistenteEmDisciplina() {
        // Criação de uma disciplina válida
        DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setIdDisciplina(UUID.randomUUID());
        disciplina.setTitulo("Algoritmos");
        disciplina.setDescricao("Introdução a Algoritmos");

        UUID idDisciplina = disciplina.getIdDisciplina();
        UUID idProfessorInexistente = UUID.randomUUID(); // Professor inexistente

        // Mocking de repositórios
        when(usuarioRepository.findById(idProfessorInexistente)).thenReturn(Optional.empty()); // Simulando professor não encontrado
        when(disciplinaRepository.findById(idDisciplina)).thenReturn(Optional.of(disciplina));

        // Chama o método para alocar o professor inexistente à disciplina
        ResponseEntity<Object> response = disciplinaController.atrelarProfessor(idDisciplina, idProfessorInexistente);

        // Verificações
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Professor não encotrado", response.getBody());
        verify(disciplinaRepository, never()).save(any(DisciplinaModel.class)); // O save não deve ser chamado
    }

}
