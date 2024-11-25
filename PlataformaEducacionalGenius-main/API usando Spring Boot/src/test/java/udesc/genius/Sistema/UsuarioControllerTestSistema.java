package udesc.genius.Sistema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTestSistema {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ct01_cadastroComSucesso() throws Exception {
        String usuarioJson = """
            {
                "nome": "Maria Silva",
                "login": "maria",
                "senha": "senha@123",
                "tipo": 1,
                "email": "maria@email.comm"
            }
        """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson))
                .andExpect(status().isCreated())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("idUsuario")));
    }

    @Test
    void ct02_camposVazios() throws Exception {
        String usuarioJson = """
            {
                "nome": " ",
                "login": "maria",
                "senha": "senha@123",
                "tipo": 1,
                "email": "mariaa@email.com"
            }
        """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ct03_emailDuplicado() throws Exception {
        // Primeiro cadastro
        String usuarioJson1 = """
            {
                "nome": "Maria Silva",
                "login": "maria",
                "senha": "senha@123",
                "tipo": 1,
                "email": "maria@email.com"
            }
        """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson1))
                .andExpect(status().isCreated());

        // Segundo cadastro com o mesmo e-mail
        String usuarioJson2 = """
            {
                "nome": "Maria Silva",
                "login": "maria123",
                "senha": "senha@123",
                "tipo": 1,
                "email": "maria@email.com"
            }
        """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioJson2))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email já cadastrado"));
    }
}
