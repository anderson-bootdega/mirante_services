package br.com.mirante.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "gestao_eventos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GestaoEventos implements Serializable {
    
    private static final long serialVersionUID = 12347689L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título é obrigatório.")
    @Size(max = 100, message = "Título deve ter no máximo 100 caracteres.")
    private String titulo;
    
    @NotBlank(message = "Descrição é obrigatória.")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres.")
    private String descricao;
    
    @FutureOrPresent(message = "Atenção: Data e hora devem ser superior a hoje.")
    private LocalDateTime dataHora;
    
    @NotBlank(message = "Local é obrigatório.")
    @Size(max = 200, message = "Local deve ter no máximo 200 caracteres.")
    private String local;
    
    private Boolean deleted = false;
    
}
