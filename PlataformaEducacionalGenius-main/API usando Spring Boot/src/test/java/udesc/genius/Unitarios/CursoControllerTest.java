package udesc.genius.Unitarios;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.CursoController;
import udesc.genius.dtos.CursoRecordDto;
import udesc.genius.models.CursoModel;
import udesc.genius.repositories.CursoRepository;

import java.util.Optional;
import java.util.UUID;

public class CursoControllerTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoController cursoController;

    private CursoRecordDto cursoRecordDto;
    private CursoModel cursoModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
       
        cursoRecordDto = new CursoRecordDto("Engenharia de Software", "Curso de graduação");

        cursoModel = new CursoModel();
        cursoModel.setTitulo(cursoRecordDto.titulo());
        cursoModel.setDescricao(cursoRecordDto.descricao());
        cursoModel.setIdCurso(UUID.randomUUID());
    }

    //CT10 passa
    @Test
    void testCriarCursoComDadosValidos() {
       
        when(cursoRepository.save(any(CursoModel.class))).thenReturn(cursoModel);

        ResponseEntity<Object> response = cursoController.salvarCurso(cursoRecordDto);

        assertEquals(201, response.getStatusCodeValue());

        assertNotNull(response.getBody());
        assertEquals(cursoModel, response.getBody());
        
        verify(cursoRepository, times(1)).save(any(CursoModel.class));
    }

    //CT11 não passa pois não possuí validação no back
    @Test
    void testCriarCursoComNomeVazio() {

        CursoRecordDto cursoComNomeVazio = new CursoRecordDto(" ", "Curso de graduação");

        ResponseEntity<Object> response = cursoController.salvarCurso(cursoComNomeVazio);

        assertEquals(400, response.getStatusCodeValue());

        assertNotNull(response.getBody());
        assertEquals("Nome inválido - curso não criado.", response.getBody());

        verify(cursoRepository, times(0)).save(any(CursoModel.class));
    }
}
