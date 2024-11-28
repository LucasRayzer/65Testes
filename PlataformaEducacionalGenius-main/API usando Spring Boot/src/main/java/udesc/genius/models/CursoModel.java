package udesc.genius.models;

import java.io.Serializable;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;

import lombok.Data;

@Entity
@Table(name="TB_CURSO") @Data
public class CursoModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID idCurso;
    
    private String titulo;
    private String descricao;

    @JsonBackReference
    @ManyToMany(mappedBy = "cursos", cascade = CascadeType.REMOVE)
    private List<UsuarioModel> usuarios;

    @OneToMany(mappedBy = "curso", orphanRemoval = true)
    private List<DisciplinaModel> disciplinas;
    
    public CursoModel(){
        disciplinas = new ArrayList<>();
    }
    
    public void removeDisciplina(DisciplinaModel disciplina) {
        this.disciplinas.remove(disciplina);
    }
    
    public void addDisciplina(DisciplinaModel disciplina) {
        this.disciplinas.add(disciplina);
    }
    
}