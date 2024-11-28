package udesc.genius.Integração;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

import udesc.genius.controllers.UsuarioController;
import udesc.genius.controllers.UsuarioCursoController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;

import java.util.UUID;
import java.util.List;

@SpringBootTest
class UsuarioCursoControllerTestInteg {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioCursoController usuarioCursoController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    private UUID alunoId;
    private UUID cursoId;

    @BeforeEach
    void setUp() {
        CursoModel curso = new CursoModel();
        curso.setTitulo("Engenharia de Software");
        curso.setDescricao("Curso de Engenharia de Software");
        curso = cursoRepository.save(curso);
        cursoId = curso.getIdCurso();

        UsuarioModel aluno = new UsuarioModel();
        aluno.setNome("Lucas Rayzer");
        aluno.setLogin("lucas_rayzer");
        aluno.setSenha("senha123");
        aluno.setEmail("lucas@example.com");
        aluno.setTipo(1); 
        aluno = usuarioRepository.save(aluno);
        alunoId = aluno.getIdUsuario();
    }

    @Transactional
    @Test
    void testCadastrarEAlocarAluno() {
        ResponseEntity<Object> response = usuarioCursoController.adicionarCurso(alunoId, cursoId);

        assertEquals(200, response.getStatusCodeValue(), "Aluno não foi alocado ao curso corretamente.");

        UsuarioModel aluno = usuarioRepository.findById(alunoId).get();
        List<CursoModel> cursosDoAluno = aluno.getCursos();

        assertTrue(cursosDoAluno.stream().anyMatch(c -> c.getIdCurso().equals(cursoId)), 
                   "O aluno não foi alocado corretamente ao curso.");
    }


}
