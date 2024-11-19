package udesc.genius.dtos;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRecordDto(@NotBlank String nome, 
                                @NotBlank String login, 
                                @NotBlank String senha,
                                int tipo,
                                @NotBlank String email) {
}