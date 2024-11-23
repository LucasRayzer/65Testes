package udesc.genius.Unitarios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.SecaoController;
import udesc.genius.dtos.SecaoRecordDto;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.SecaoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.SecaoRepository;

@ExtendWith(MockitoExtension.class) 
public class SecaoControllerTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Mock
    private SecaoRepository secaoRepository;

    @InjectMocks
    private SecaoController secaoController;

    private DisciplinaModel disciplina;

    @BeforeEach
    public void setUp() {
       
        disciplina = new DisciplinaModel();
        disciplina.setIdDisciplina(UUID.randomUUID());
        disciplina.setTitulo("Algoritmos");
        disciplina.setDescricao("Introdução a Algoritmos");
    }
    //CT18 Passa
    @Test
    public void testCriarSecaoComDadosValidos() {
        
        SecaoRecordDto secaoRecordDto = new SecaoRecordDto("Seção A", "Descrição da Seção A");

        when(disciplinaRepository.findById(disciplina.getIdDisciplina())).thenReturn(Optional.of(disciplina));
        when(secaoRepository.save(any(SecaoModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<Object> response = secaoController.salvarSecao(disciplina.getIdDisciplina(), secaoRecordDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof SecaoModel);

        SecaoModel secaoCriada = (SecaoModel) response.getBody();
        assertEquals("Seção A", secaoCriada.getTitulo());
        assertEquals("Descrição da Seção A", secaoCriada.getDescricao());
        assertEquals(disciplina.getIdDisciplina(), secaoCriada.getDisciplina().getIdDisciplina());

        verify(disciplinaRepository, times(1)).findById(disciplina.getIdDisciplina());
        verify(secaoRepository, times(1)).save(any(SecaoModel.class));
    }
    //CT19 Testa se não tentou salvar e passa, pois não tenta na ultima linha
     @Test
    public void testCriarSecaoComDisciplinaInexistente()
    {
        SecaoRecordDto secaoRecordDto = new SecaoRecordDto("Seção A", "Descrição da Seção A");

        UUID idDisciplinaInvalido = UUID.randomUUID();
        when(disciplinaRepository.findById(idDisciplinaInvalido)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = secaoController.salvarSecao(idDisciplinaInvalido, secaoRecordDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); //404 not found, não apenas 404
        assertEquals("Disciplina não encontrada.", response.getBody()); 

        verify(disciplinaRepository, times(1)).findById(idDisciplinaInvalido);
        verify(secaoRepository, times(0)).save(any(SecaoModel.class)); // nao deve tentar salvar a secao
    }
    //CT20 não passou pois não temos verificação no back
    @Test
    public void testCriarSecaoComNomeVazio() {
        SecaoRecordDto secaoRecordDto = new SecaoRecordDto("", "Descrição da Seção A");

        UUID idDisciplinaValida = disciplina.getIdDisciplina();
        when(disciplinaRepository.findById(idDisciplinaValida)).thenReturn(Optional.of(disciplina));

        ResponseEntity<Object> response = secaoController.salvarSecao(idDisciplinaValida, secaoRecordDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nome da seção inválido", response.getBody());

        verify(disciplinaRepository, times(1)).findById(idDisciplinaValida);
        verify(secaoRepository, times(0)).save(any(SecaoModel.class));
    }
}
