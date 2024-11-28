package udesc.genius.Integração;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.SecaoModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.SecaoRepository;
import udesc.genius.controllers.SecaoController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecaoUsuarioControllerTestInteg {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private SecaoRepository secaoRepository;

    @Autowired
    private SecaoController secaoController;

    @BeforeEach
    public void setup() {
        secaoRepository.deleteAll();
        disciplinaRepository.deleteAll();
    }

@Test
public void testGetSecoesPorDisciplina() throws Exception {
    UUID idDisciplina = UUID.randomUUID();
    DisciplinaModel disciplina = new DisciplinaModel();
        disciplina.setIdDisciplina(idDisciplina);

        SecaoModel secao1 = new SecaoModel();
        secao1.setIdSecao(UUID.randomUUID());
        secao1.setDisciplina(disciplina);

        SecaoModel secao2 = new SecaoModel();
        secao2.setIdSecao(UUID.randomUUID());
        secao2.setDisciplina(disciplina);

        List<SecaoModel> secoes = Arrays.asList(secao1, secao2);
        disciplina.setSecoes(secoes);

        assertNotNull(secoes);
        assertEquals(2, secoes.size());

    }

}
