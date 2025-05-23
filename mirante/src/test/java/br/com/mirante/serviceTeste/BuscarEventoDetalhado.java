package br.com.mirante.serviceTeste;

import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.repository.GestaoEventosRepository;
import br.com.mirante.service.BuscarEventoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BuscarEventoDetalhadoServiceTest {
    
    @InjectMocks
    private BuscarEventoService buscarEventoService;
    
    @Mock
    private GestaoEventosRepository gestaoEventosRepository;
    
    @Test
    void deveRetornarEventoQuandoEncontrado() {
        // Arrange
        Long id = 1L;
        GestaoEventos evento = new GestaoEventos(id, "Titulo", "Descrição", LocalDateTime.now(), "Local", false);
        
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.of(evento));
        
        // Act
        Optional<GestaoEventos> resultado = buscarEventoService.buscarEventosDetalhado(id);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        verify(gestaoEventosRepository).findById(id);
    }
    
    @Test
    void deveRetornarOptionalVazioQuandoNaoEncontrado() {
        // Arrange
        Long id = 2L;
        when(gestaoEventosRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        Optional<GestaoEventos> resultado = buscarEventoService.buscarEventosDetalhado(id);
        
        // Assert
        assertFalse(resultado.isPresent());
        verify(gestaoEventosRepository).findById(id);
    }
}
