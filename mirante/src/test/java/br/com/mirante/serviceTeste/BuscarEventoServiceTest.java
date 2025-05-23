package br.com.mirante.serviceTeste;

import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.repository.GestaoEventosRepository;
import br.com.mirante.service.BuscarEventoService;
import jakarta.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BuscarEventoServiceTest {
    
    @InjectMocks
    private BuscarEventoService buscarEventoService;
    
    @Mock
    private GestaoEventosRepository gestaoEventosRepository;
    
    @Test
    void deveRetornarEventosPaginadosComSucesso() {
        // Arrange
        int pagina = 0;
        int tamanho = 2;
        
        List<GestaoEventos> eventosMock = List.of(
                new GestaoEventos(1L, "Evento 1", "Descrição 1", LocalDateTime.now(), "Local 1", false),
                new GestaoEventos(2L, "Evento 2", "Descrição 2", LocalDateTime.now(), "Local 2", false)
        );
        Page<GestaoEventos> paginaMock = new PageImpl<>(eventosMock);
        
        when(gestaoEventosRepository.findAll(PageRequest.of(pagina, tamanho)))
                .thenReturn(paginaMock);
        
        // Act
        Page<GestaoEventos> resultado = buscarEventoService.buscarEventos(pagina, tamanho);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        verify(gestaoEventosRepository).findAll(PageRequest.of(pagina, tamanho));
    }
    
    @Test
    void deveLancarExcecaoQuandoPaginaNaoTemEventos() {
        // Arrange
        int pagina = 0;
        int tamanho = 5;
        Page<GestaoEventos> paginaVazia = Page.empty();
        
        when(gestaoEventosRepository.findAll(PageRequest.of(pagina, tamanho)))
                .thenReturn(paginaVazia);
        
        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                buscarEventoService.buscarEventos(pagina, tamanho)
        );
        
        assertEquals("Nenhum evento encontrado na página " + tamanho, exception.getMessage());
        verify(gestaoEventosRepository).findAll(PageRequest.of(pagina, tamanho));
    }
}
