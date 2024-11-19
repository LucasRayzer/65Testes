package udesc.genius.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import udesc.genius.models.UsuarioModel;

import java.util.UUID;

public interface UsuarioRepository  extends JpaRepository<UsuarioModel, UUID> {
}