package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.UsuarioModel;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel, UUID> {
    Optional<UsuarioModel> findByEmail(String email);
}