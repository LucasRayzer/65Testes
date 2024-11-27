package udesc.genius.Unitarios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.UsuarioCursoController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

import java.util.*;

public class UsuarioCursoControllerTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private UsuarioCursoController usuarioCursoController;

    private UsuarioModel usuarioModel;
    private CursoModel cursoModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simula um usuário existente
        usuarioModel = new UsuarioModel();
        usuarioModel.setIdUsuario(UUID.randomUUID());
        usuarioModel.setNome("João Silva");

        // Simula um curso existente
        cursoModel = new CursoModel();
        cursoModel.setIdCurso(UUID.randomUUID());
        cursoModel.setTitulo("Engenharia de Software");
        cursoModel.setDescricao("Curso de graduação");
    }

    //CT12 passa
    @Test
    public void testAdicionarCursoComSucesso() {

        UUID idUsuario = UUID.randomUUID();
        UUID idCurso = UUID.randomUUID();
    
        UsuarioModel usuario = new UsuarioModel();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("João Silva");
        usuario.setCursos(new ArrayList<>());
    
        CursoModel curso = new CursoModel();
        curso.setIdCurso(idCurso);
        curso.setTitulo("Engenharia de Software");
    
        Mockito.when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));
    
        ResponseEntity<Object> response = usuarioCursoController.adicionarCurso(idUsuario, idCurso);
    
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    
        Assertions.assertThat(response.getBody()).isEqualTo("Curso adicionado com sucesso");

        Assertions.assertThat(usuario.getCursos()).contains(curso);
    
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
    }
    //CT13 passa já que foi testado os métodos que não foram chamados
    @Test
    public void testAdicionarCursoAlunoInexistente() {

        UUID idUsuarioInexistente = UUID.randomUUID();
        UUID idCurso = cursoModel.getIdCurso();

        Mockito.when(usuarioRepository.findById(idUsuarioInexistente)).thenReturn(Optional.empty());
        Mockito.when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(cursoModel));

        ResponseEntity<Object> response = usuarioCursoController.adicionarCurso(idUsuarioInexistente, idCurso);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(response.getBody()).isEqualTo("Usuário não encontrado");

        Mockito.verify(usuarioRepository, Mockito.never()).save(any());
    }

    
}
