package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.DisciplinaModel;

import java.util.UUID;

public interface DisciplinaRepository extends JpaRepository<DisciplinaModel, UUID> {
    
}