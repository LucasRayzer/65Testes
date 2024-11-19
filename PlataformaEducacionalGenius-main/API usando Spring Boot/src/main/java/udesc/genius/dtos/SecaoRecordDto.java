package udesc.genius.dtos;

import jakarta.validation.constraints.NotBlank;

public record SecaoRecordDto(@NotBlank String titulo,
                                @NotBlank String descricao) {
    
}