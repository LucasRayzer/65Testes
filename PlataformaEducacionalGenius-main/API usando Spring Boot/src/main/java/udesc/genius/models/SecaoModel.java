package udesc.genius.models;

import java.io.Serializable;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="TB_SECAO") @Data
public class SecaoModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSecao;
    private String titulo;
    private String descricao;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="idDisciplina")
    private DisciplinaModel disciplina;
}