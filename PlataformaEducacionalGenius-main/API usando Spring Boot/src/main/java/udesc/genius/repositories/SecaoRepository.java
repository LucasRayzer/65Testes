package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.SecaoModel;

import java.util.UUID;

public interface SecaoRepository extends JpaRepository<SecaoModel, UUID> {
    
}