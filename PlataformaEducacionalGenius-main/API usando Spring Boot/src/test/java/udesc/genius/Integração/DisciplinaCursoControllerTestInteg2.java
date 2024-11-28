package udesc.genius.Integração;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import udesc.genius.models.CursoModel;
import udesc.genius.models.DisciplinaModel;


public class DisciplinaCursoControllerTestInteg2 {

       @Test
    void testDeletarDisciplinaDeCurso() {
        CursoModel curso = new CursoModel();
        curso.setTitulo("Engenharia de Software");

        DisciplinaModel disciplina1 = new DisciplinaModel();
        disciplina1.setTitulo("Desenvolvimento de Software");
        disciplina1.setCurso(curso);

        DisciplinaModel disciplina2 = new DisciplinaModel();
        disciplina2.setTitulo("Banco de Dados");
        disciplina2.setCurso(curso);


        curso.addDisciplina(disciplina1);
        curso.addDisciplina(disciplina2);

        assertEquals(2, curso.getDisciplinas().size(), "A quantidade inicial de disciplinas deve ser 2.");

        curso.removeDisciplina(disciplina1);

        assertEquals(1, curso.getDisciplinas().size(), "A quantidade de disciplinas deve ser 1 após a exclusão.");
        assertFalse(curso.getDisciplinas().contains(disciplina1), "A disciplina excluída não deve estar presente.");
    }
}
