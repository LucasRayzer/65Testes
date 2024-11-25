package udesc.genius.Integração;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class DisciplinaControllerTestInteg {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private UsuarioModel professor;
    private DisciplinaModel disciplina;

    @BeforeEach
    void setup() {
        professor = new UsuarioModel();
        professor.setNome("Professor Teste");
        professor.setTipo(2); 
        professor.setLogin("professor1");
        professor.setSenha("senha123");
        professor.setEmail("professor@teste.com");
        usuarioRepository.save(professor);

        disciplina = new DisciplinaModel();
        disciplina.setTitulo("Disciplina Teste");
        disciplina.setDescricao("D001");
        disciplina.setProfessor(professor);
        disciplinaRepository.save(disciplina);

        professor.getDisciplinas().add(disciplina);
        usuarioRepository.save(professor);
    }

    @Test
    void testDesatrelarProfessorComSucesso() throws Exception {
        mockMvc.perform(put("/professor/{idProfessor}/disciplinas/{idDisciplina}",
                        professor.getIdUsuario(), disciplina.getIdDisciplina()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Professor desvinculado com sucesso."));
    }


}
