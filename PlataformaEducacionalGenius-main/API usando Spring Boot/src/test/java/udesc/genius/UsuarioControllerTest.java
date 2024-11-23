package udesc.genius;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.UsuarioController;
import udesc.genius.dtos.UsuarioRecordDto;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UsuarioControllerTest {
  @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CursoRepository cursoRepository;

    private UsuarioRecordDto usuarioDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        
        usuarioDto = new UsuarioRecordDto(
            "Lucas",
            "lucas",
            "teste1234",
            1,
            "lucas@gmail.com"
        );
    }
    //CT01
    @Test
    void testSalvarUsuario_Sucesso() {
        
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

      
        UsuarioModel usuarioSalvo = new UsuarioModel();
        usuarioSalvo.setIdUsuario(null);
        usuarioSalvo.setNome("Lucas");
        usuarioSalvo.setLogin("lucas");
        usuarioSalvo.setSenha("teste1234");
        usuarioSalvo.setTipo(1);
        usuarioSalvo.setEmail("lucas@gmail.com");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioSalvo);

    
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

       
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(usuarioSalvo, response.getBody()); 
        verify(usuarioRepository, times(1)).save(any(UsuarioModel.class)); 
    }
     //CT02 o teste não passa devido a erro de implementação do backend, não realiza a validação de caracteres
    @Test
    void testSalvarUsuario_CamposVazios() {
        
        usuarioDto = new UsuarioRecordDto(
            " ",  
            "lucas", 
            " ",   
            1,
            "lucas@gmail.com"
        );

       
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

        assertEquals(400, response.getStatusCodeValue()); 
        assertEquals("Todos os campos devem ser preenchidos", response.getBody()); 
        verify(usuarioRepository, times(0)).save(any(UsuarioModel.class)); 
    }
    //CT03 segue o mesmo caminho, não possui validação
    @Test
    void testSalvarUsuario_EmailInvalido() {
        
        usuarioDto = new UsuarioRecordDto(
            "Lucas",
            "lucas@", 
            "teste1234",
            1,
            "lucas@"
        );

        
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

       
        assertEquals(400, response.getStatusCodeValue()); 
        assertEquals("E-mail inválido", response.getBody()); 
        verify(usuarioRepository, times(0)).save(any()); 
    }
    //CT04 passa, pois isso ele valida
    @Test
    void testSalvarUsuario_EmailJaCadastrado() {
        
        usuarioDto = new UsuarioRecordDto(
            "Lucas",
            "lucas@gmail.com", 
            "teste1234",
            1,
            "lucas@gmail.com"
        );

        UsuarioModel usuarioExistente = new UsuarioModel();
        usuarioExistente.setEmail("lucas@gmail.com");
        List<UsuarioModel> usuarios = new ArrayList<>();
        usuarios.add(usuarioExistente);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

       
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Email já cadastrado", response.getBody()); 
        verify(usuarioRepository, times(0)).save(any()); 
    }
    //CT05 não passa pois este não é validado no backend
    @Test
    void testSalvarUsuario_SenhaMenorQue8Caracteres() {
        
        usuarioDto = new UsuarioRecordDto(
            "Lucas",
            "lucas@gmail.com",
            "123", 
            1,
            "lucas@gmail.com"
        );

        
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

       
        assertEquals(400, response.getStatusCodeValue()); 
        assertEquals("A senha deve ter pelo menos 8 caracteres", response.getBody());
        verify(usuarioRepository, times(0)).save(any()); 
    }
    //CT06 passa
    @Test
    void testDeletarUsuario_Sucesso_UsandoDTO() {

        UUID usuarioId = UUID.randomUUID();
        UsuarioModel usuarioMock = new UsuarioModel();
        usuarioMock.setIdUsuario(usuarioId);
        usuarioMock.setNome("Lucas");
        usuarioMock.setLogin("lucas");
        usuarioMock.setSenha("teste1234");
        usuarioMock.setTipo(1);
        usuarioMock.setEmail("lucas@gmail.com");
        usuarioMock.setCursos(new ArrayList<>());
        usuarioMock.setDisciplinas(new ArrayList<>());

        CursoModel cursoMock = new CursoModel();
        cursoMock.setIdCurso(UUID.randomUUID());
        cursoMock.setTitulo("Teste");
        cursoMock.setDescricao("Teste");
        cursoMock.setUsuarios(new ArrayList<>());
        cursoMock.setDisciplinas(new ArrayList<>());
        cursoMock.getUsuarios().add(usuarioMock);


        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));
        when(cursoRepository.findAll()).thenReturn(List.of(cursoMock));

        when(cursoRepository.save(any(CursoModel.class))).thenReturn(cursoMock);

        ResponseEntity<Object> response = usuarioController.deletarUsuario(usuarioId);

        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Usuario deletado com sucesso", response.getBody());

        verify(usuarioRepository, times(1)).delete(usuarioMock);
    }
    //CT07 passa
    @Test
    void testDeletarUsuario_UsuarioInexistente() {
    
        UUID usuarioIdInexistente = UUID.randomUUID();  
    
        when(usuarioRepository.findById(usuarioIdInexistente)).thenReturn(Optional.empty());
        
        ResponseEntity<Object> response = usuarioController.deletarUsuario(usuarioIdInexistente);

        assertEquals(404, response.getStatusCodeValue());  
        assertEquals("Usuario não encontrado", response.getBody()); 

        verify(usuarioRepository, times(0)).delete(any(UsuarioModel.class)); 

        verify(cursoRepository, times(0)).save(any(CursoModel.class));  
    }
    //CT08 passa
    @Test
    void testAutenticacaoComDadosCorretos() {
      
        UsuarioRecordDto usuarioDto = new UsuarioRecordDto(
            "lucas", 
            "Lucas", 
            "teste1234",  
            1,  
            "lucas@gmail.com"  
        );
    
    
        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("lucas@gmail.com");
        usuarioValido.setSenha("teste1234");
        usuarioValido.setLogin("lucas");
        usuarioValido.setNome("Lucas");
        usuarioValido.setTipo(1);
    
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioValido);
        when(usuarioRepository.findByEmail(usuarioDto.email())).thenReturn(Optional.of(usuarioValido));  
    
        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);
    
        assertEquals(201, response.getStatusCodeValue());
    
        assertEquals(usuarioValido, response.getBody());
    
        verify(usuarioRepository, times(1)).save(any(UsuarioModel.class));
    }
    //CT09 não passa, pois este acaba não possuindo um método no back que valide a senha 
    @Test
    void testAutenticacaoComSenhaIncorreta() {
        
        UsuarioRecordDto usuarioDto = new UsuarioRecordDto(
            "lucas",  
            "Lucas",  
            "teste0000",  
            1,  
            "lucas@gmail.com"  
        );

        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("lucas@gmail.com");
        usuarioValido.setSenha("teste1234"); 
        usuarioValido.setLogin("lucas");
        usuarioValido.setNome("Lucas");
        usuarioValido.setTipo(1);

        when(usuarioRepository.findByEmail(usuarioDto.email())).thenReturn(Optional.of(usuarioValido));

        ResponseEntity<Object> response = usuarioController.salvarUsuario(usuarioDto);

        assertEquals(401, response.getStatusCodeValue());

        assertEquals("Erro de autenticação. Senha incorreta.", response.getBody());

        verify(usuarioRepository, times(0)).save(any(UsuarioModel.class));
    }
}
