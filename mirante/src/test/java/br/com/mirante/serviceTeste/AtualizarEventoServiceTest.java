package br.com.mirante.serviceTeste;

import br.com.mirante.domain.AtualizarEventoDTO;
import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.repository.GestaoEventosRepository;
import br.com.mirante.service.SalvarEventoService;
import jakarta.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AtualizarEventoServiceTest {
    
    @InjectMocks
    private SalvarEventoService atualizarEventoService;
    
    @Mock
    private GestaoEventosRepository gestaoEventosRepository;
    
    @Test
    void deveAtualizarEventoComSucesso() {
        // Arrange
        Long id = 1L;
        GestaoEventos eventoExistente = new GestaoEventos(id, "Titulo Antigo", "Desc Antiga", LocalDateTime.now().minusDays(1), "Local Antigo", false);
        AtualizarEventoDTO dto = new AtualizarEventoDTO("Titulo Novo", "Desc Nova", LocalDateTime.now(), "Local Novo");
        
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.of(eventoExistente));
        when(gestaoEventosRepository.save(any(GestaoEventos.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        GestaoEventos resultado = atualizarEventoService.atualizarEvento(id, dto);
        
        // Assert
        assertEquals(dto.titulo(), resultado.getTitulo());
        assertEquals(dto.descricao(), resultado.getDescricao());
        assertEquals(dto.dataHora(), resultado.getDataHora());
        assertEquals(dto.local(), resultado.getLocal());
        verify(gestaoEventosRepository).findById(id);
        verify(gestaoEventosRepository).save(eventoExistente);
    }
    
    @Test
    void deveLancarEntityNotFoundExceptionQuandoEventoNaoEncontrado() {
        // Arrange
        Long id = 1L;
        AtualizarEventoDTO dto = new AtualizarEventoDTO("Titulo", "Desc", LocalDateTime.now(), "Local");
        
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                atualizarEventoService.atualizarEvento(id, dto)
        );
        
        assertEquals("Evento com id " + id + " não encontrado", exception.getMessage());
        verify(gestaoEventosRepository).findById(id);
        verify(gestaoEventosRepository, never()).save(any());
    }
}
