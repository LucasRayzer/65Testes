package udesc.genius.dtos;

import jakarta.validation.constraints.NotBlank;

public record NotificacaoRecordDto(@NotBlank String titulo,
                                    @NotBlank String descricao,
                                    @NotBlank Boolean visualizada ) {
    
}