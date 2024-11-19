package udesc.genius.dtos;

import jakarta.validation.constraints.NotBlank;

public record CursoRecordDto(@NotBlank String titulo,
                                @NotBlank String descricao) {
    
}