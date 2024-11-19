package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.CursoModel;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoModel, UUID> {

}