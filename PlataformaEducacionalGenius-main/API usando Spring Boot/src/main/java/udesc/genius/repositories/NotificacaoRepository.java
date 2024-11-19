package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.NotificacaoModel;

import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<NotificacaoModel, UUID> {
    
}