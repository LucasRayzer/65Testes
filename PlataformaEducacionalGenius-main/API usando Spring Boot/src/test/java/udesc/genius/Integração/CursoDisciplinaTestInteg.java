/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udesc.genius.Integração;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udesc.genius.controllers.DisciplinaController;
import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.repositories.CursoRepository;
import udesc.genius.repositories.DisciplinaRepository;

public class CursoDisciplinaTestInteg {
    @InjectMocks
    private DisciplinaController disciplinaController;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    private CursoModel curso;
    private List<DisciplinaModel> disciplinas;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        curso = new CursoModel();
        curso.setIdCurso(UUID.randomUUID());
        curso.setTitulo("Ciência da Computação");

        disciplinas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            DisciplinaModel disciplina = new DisciplinaModel();
            disciplina.setIdDisciplina(UUID.randomUUID());
            disciplina.setTitulo("Disciplina " + i);
            disciplina.setCurso(curso);
            disciplinas.add(disciplina);
        }

        curso.setDisciplinas(disciplinas);
    }

    @Test
    public void testGetTodasDisciplinas() {
        assertNotNull(curso.getDisciplinas());
        assertEquals(3, curso.getDisciplinas().size());
    }
}


