package udesc.genius.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinTable;
import java.util.ArrayList;

import lombok.Data;

@Entity
@Table(name="TB_USUARIO") @Data
public class UsuarioModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID idUsuario;

    private String nome;
    private String login;
    private String senha;
    private int tipo;
    private String email;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
        name="TB_USUARIO_CURSO",
        joinColumns = @JoinColumn(name="idUsuario"),
        inverseJoinColumns = @JoinColumn(name="idCurso")
    )
    private List<CursoModel> cursos;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<DisciplinaModel> disciplinas = new ArrayList<>();
    
    public UsuarioModel() {
    this.cursos = new ArrayList<>();
}
}