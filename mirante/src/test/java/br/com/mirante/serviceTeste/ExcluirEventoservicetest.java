package br.com.mirante.serviceTeste;

import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.domain.exception.BusinessException;
import br.com.mirante.repository.GestaoEventosRepository;
import br.com.mirante.service.ExcluirEventoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
class ExcluirEventoServiceTest {
    
    @InjectMocks
    private ExcluirEventoService excluirEventoService;
    
    @Mock
    private GestaoEventosRepository gestaoEventosRepository;
    
    @Test
    void deveExcluirEventoComSucesso() {
        // Arrange
        Long id = 1L;
        GestaoEventos evento = new GestaoEventos(id, "Título", "Descrição", LocalDateTime.now(), "Local", false);
        
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.of(evento));
        when(gestaoEventosRepository.save(any(GestaoEventos.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        excluirEventoService.excluirEvento(id);
        
        // Assert
        assertTrue(evento.getDeleted());
        verify(gestaoEventosRepository).findById(id);
        verify(gestaoEventosRepository).save(evento);
    }
    
    @Test
    void deveLancarExcecaoQuandoEventoNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                excluirEventoService.excluirEvento(id)
        );
        
        assertEquals("Evento com ID " + id + " não encontrado.", exception.getMessage());
        verify(gestaoEventosRepository).findById(id);
        verify(gestaoEventosRepository, never()).save(any());
    }
    
    @Test
    void deveLancarExcecaoQuandoEventoJaExcluido() {
        // Arrange
        Long id = 1L;
        GestaoEventos evento = new GestaoEventos(id, "Título", "Descrição", LocalDateTime.now(), "Local", true);
        
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.of(evento));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                excluirEventoService.excluirEvento(id)
        );
        
        assertEquals("O evento já está marcado como excluído.", exception.getMessage());
        verify(gestaoEventosRepository).findById(id);
        verify(gestaoEventosRepository, never()).save(any());
    }
}

