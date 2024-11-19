package udesc.genius.dtos;

import jakarta.validation.constraints.NotBlank;

public record DisciplinaRecordDto(@NotBlank String titulo,
                                    @NotBlank String descricao){
    
}