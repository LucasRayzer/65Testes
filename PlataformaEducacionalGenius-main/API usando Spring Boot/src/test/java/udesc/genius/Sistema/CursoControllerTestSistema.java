
package udesc.genius.Sistema;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import udesc.genius.dtos.CursoRecordDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CursoControllerTestSistema {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ct04_cursoCadastradoComSucesso() throws Exception {
        // Entrada mínima válida
        CursoRecordDto cursoMinimoValido = new CursoRecordDto("Eng", "Cur");
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoMinimoValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Eng"))
                .andExpect(jsonPath("$.descricao").value("Cur"));

        // Entrada no intervalo
        CursoRecordDto cursoValido = new CursoRecordDto("Engenharia de Software", "Curso noturno");
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Engenharia de Software"))
                .andExpect(jsonPath("$.descricao").value("Curso noturno"));

        // Entrada máxima válida
        CursoRecordDto cursoMaximoValido = new CursoRecordDto("A".repeat(20), "A".repeat(50));
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoMaximoValido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("A".repeat(20)))
                .andExpect(jsonPath("$.descricao").value("A".repeat(50)));
    }

    @Test
    void ct05_cadastroCamposInvalidos() throws Exception {
        // Nome vazio
        CursoRecordDto cursoNomeVazio = new CursoRecordDto("", "Descrição válida");
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoNomeVazio)))
                .andExpect(status().isBadRequest());

        // Descrição vazia
        CursoRecordDto cursoDescricaoVazia = new CursoRecordDto("Nome válido", "");
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoDescricaoVazia)))
                .andExpect(status().isBadRequest());

        // Nome excedendo limite
        CursoRecordDto cursoNomeExcedido = new CursoRecordDto("A".repeat(21), "Descrição válida");
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoNomeExcedido)))
                .andExpect(status().isBadRequest());

        // Descrição excedendo limite
        CursoRecordDto cursoDescricaoExcedida = new CursoRecordDto("Nome válido", "A".repeat(51));
        mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cursoDescricaoExcedida)))
                .andExpect(status().isBadRequest());
    }
}
