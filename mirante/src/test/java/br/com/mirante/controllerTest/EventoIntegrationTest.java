package br.com.mirante.controllerTest;

import br.com.mirante.controller.GestaoEventoController;
import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.service.BuscarEventoService;
import br.com.mirante.service.ExcluirEventoService;
import br.com.mirante.service.SalvarEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@WebMvcTest(GestaoEventoController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventoIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    // Aqui você deve usar @MockBean para criar mocks que o Spring injeta no controller
    @MockBean
    private BuscarEventoService buscarEventoService;
    
    @MockBean
    private ExcluirEventoService excluirEventoService;
    
    @MockBean
    private SalvarEventoService salvarEventoService;
    
    @Test
    void deveSalvarEventoComSucesso() throws Exception {
        GestaoEventos evento = new GestaoEventos(
                null,
                "Evento Integração",
                "Teste de integração com H2",
                LocalDateTime.now().plusDays(1),
                "Local X",
                false
        );
        
        // Configure o comportamento do mock, se necessário
        Mockito.when(salvarEventoService.salvarEvento(Mockito.any())).thenReturn(evento);
        
        mockMvc.perform(post("/v1/gestao-eventos/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evento)))
                .andExpect(status().isCreated());
    }
}
