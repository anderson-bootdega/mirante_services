package br.com.mirante.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AtualizarEventoDTO(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        String titulo,
        
        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String descricao,
        
        @NotNull(message = "Data e hora é obrigatória")
        @FutureOrPresent(message = "Data e hora deve ser presente ou futura")
        LocalDateTime dataHora,
        
        @NotBlank(message = "Local é obrigatório")
        @Size(max = 200, message = "Local deve ter no máximo 200 caracteres")
        String local
) {}
