package udesc.genius.models;

import java.io.Serializable;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name="TB_DISCIPLINA") @Data
public class DisciplinaModel implements Serializable{
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID idDisciplina;
    private String titulo;
    private String descricao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="idCurso")
    private CursoModel curso;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="idProfessor")
    private UsuarioModel professor;

    @OneToMany (mappedBy="disciplina", orphanRemoval = true)
    private List<SecaoModel> secoes;
}