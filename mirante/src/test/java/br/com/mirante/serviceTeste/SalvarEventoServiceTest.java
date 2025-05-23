package br.com.mirante.serviceTeste;

import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.domain.exception.BusinessException;
import br.com.mirante.repository.GestaoEventosRepository;
import br.com.mirante.service.SalvarEventoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class SalvarEventoServiceTest {
    
    @InjectMocks
    private SalvarEventoService salvarEventoService;
    
    @Mock
    private GestaoEventosRepository gestaoEventosRepository;
    
    @Test
    void deveSalvarEventoComSucesso() {
        // Arrange
        GestaoEventos evento = new GestaoEventos(null, "Titulo", "Descricao", LocalDateTime.now(), "Local", false);
        GestaoEventos eventoSalvo = new GestaoEventos(1L, "Titulo", "Descricao", LocalDateTime.now(), "Local", false);
        
        when(gestaoEventosRepository.save(evento)).thenReturn(eventoSalvo);
        
        // Act
        GestaoEventos resultado = salvarEventoService.salvarEvento(evento);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(gestaoEventosRepository).save(evento);
    }
    
    @Test
    void deveLancarBusinessExceptionQuandoDataIntegrityViolationException() {
        // Arrange
        GestaoEventos evento = new GestaoEventos(null, "Titulo", "Descricao", LocalDateTime.now(), "Local", false);
        
        when(gestaoEventosRepository.save(evento))
                .thenThrow(new DataIntegrityViolationException("Chave duplicada"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                salvarEventoService.salvarEvento(evento)
        );
        
        assertTrue(exception.getMessage().contains("Violação de integridade"));
        verify(gestaoEventosRepository).save(evento);
    }
    
    @Test
    void deveLancarBusinessExceptionQuandoExceptionGenerica() {
        // Arrange
        GestaoEventos evento = new GestaoEventos(null, "Titulo", "Descricao", LocalDateTime.now(), "Local", false);
        
        when(gestaoEventosRepository.save(evento))
                .thenThrow(new RuntimeException("Erro inesperado"));
        
        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                salvarEventoService.salvarEvento(evento)
        );
        
        assertEquals("Erro ao salvar o evento", exception.getMessage());
        verify(gestaoEventosRepository).save(evento);
    }
}

