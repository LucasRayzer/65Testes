package udesc.genius;

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

    // Teste CT10 - Criar curso com dados válidos
    @Test
    void testCriarCursoComDadosValidos() {
        // Simulando o retorno do repositório
        when(cursoRepository.save(any(CursoModel.class))).thenReturn(cursoModel);

        // Chamando o método salvarCurso do controller
        ResponseEntity<Object> response = cursoController.salvarCurso(cursoRecordDto);

        // Verificando se o código de status é 201 (Created)
        assertEquals(201, response.getStatusCodeValue());

        // Verificando se o corpo da resposta não é nulo e contém o curso salvo
        assertNotNull(response.getBody());
        assertEquals(cursoModel, response.getBody());
        
        // Verificando se o método save foi chamado uma vez no repositório
        verify(cursoRepository, times(1)).save(any(CursoModel.class));
    }

    // Teste CT11 - Criar curso com nome vazio
    @Test
    void testCriarCursoComNomeVazio() {
        // Curso com nome vazio
        CursoRecordDto cursoComNomeVazio = new CursoRecordDto(" ", "Curso de graduação");

        // Chamando o método salvarCurso do controller
        ResponseEntity<Object> response = cursoController.salvarCurso(cursoComNomeVazio);

        // Verificando se o código de status é 400 (Bad Request), pois o nome é inválido
        assertEquals(400, response.getStatusCodeValue());

        // Verificando se o corpo da resposta contém a mensagem de erro esperada
        assertNotNull(response.getBody());
        assertEquals("Nome inválido - curso não criado.", response.getBody());

        // Verificando se o método save não foi chamado no repositório
        verify(cursoRepository, times(0)).save(any(CursoModel.class));
    }
}
