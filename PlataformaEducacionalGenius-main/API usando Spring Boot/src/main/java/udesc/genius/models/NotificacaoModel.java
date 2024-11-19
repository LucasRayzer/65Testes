package udesc.genius.models;

import java.io.Serializable;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;

@Entity
@Table(name="TB_NOTIFICACAO") @Data
public class NotificacaoModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID idNotificacao;
    private String titulo;
    private String descricao;
    private Boolean visualizada;
}