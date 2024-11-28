package udesc.genius.Integração;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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
class DisciplinaControllerTestInteg2 {

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
        professor.setNome("Paolo Moser");
        professor.setTipo(2); // Tipo de professor
        professor.setLogin("paolo_moser");
        professor.setSenha("senha123");
        professor.setEmail("paolo.moser@teste.com");
        usuarioRepository.save(professor);

        disciplina = new DisciplinaModel();
        disciplina.setTitulo("Matemática");
        disciplina.setDescricao("Matéria de Matemática");
        disciplinaRepository.save(disciplina);
    }

    @Test
    void testCriarDisciplinaEAlocarProfessor() throws Exception {
        mockMvc.perform(put("/disciplinas/{idDisciplina}/professor/{idProfessor}",
                disciplina.getIdDisciplina(), professor.getIdUsuario()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Matemática"))
                .andExpect(jsonPath("$.idDisciplina").value(disciplina.getIdDisciplina().toString()));
    }

}
