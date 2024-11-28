/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.genius.Integração;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import udesc.genius.controllers.UsuarioCursoController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.UsuarioRepository;


public class UsuarioCursoControllerTestInteg2 {
   @InjectMocks
    private UsuarioCursoController usuarioCursoController;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    private CursoModel curso;
    private List<UsuarioModel> usuarios;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        curso = new CursoModel();
        curso.setIdCurso(UUID.randomUUID());
        curso.setTitulo("Engenharia de Software");

        usuarios = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setIdUsuario(UUID.randomUUID());
            usuario.setNome("Usuário " + i);
            usuario.getCursos().add(curso);
            usuarios.add(usuario);
        }

        curso.setUsuarios(usuarios);
    }

    @Test
    public void testGetUsuariosCurso() {
        assertNotNull(curso.getUsuarios());
        assertEquals(3, curso.getUsuarios().size());
    }
}
