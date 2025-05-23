package br.com.mirante.controller;

import br.com.mirante.domain.DTO.AtualizarEventoDTO;
import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.service.BuscarEventoService;
import br.com.mirante.service.ExcluirEventoService;
import br.com.mirante.service.SalvarEventoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/gestao-eventos")
public class GestaoEventoController {
    
    private final BuscarEventoService buscarEventoService;
    private final ExcluirEventoService excluirEventoService;
    private final SalvarEventoService salvarEventoService;
    
    GestaoEventoController(BuscarEventoService buscarEventoService, ExcluirEventoService excluirEventoService,
                           SalvarEventoService salvarEventoService) {
        
        this.buscarEventoService = buscarEventoService;
        this.excluirEventoService = excluirEventoService;
        this.salvarEventoService = salvarEventoService;
        
    }
    
    @GetMapping("/api/events")
    public ResponseEntity<Page<GestaoEventos>> buscarEventosPaginado(@RequestParam(defaultValue = "0") int numPagina,
                                                                     @RequestParam(defaultValue = "10") int tamPagina) {
        
        return ResponseEntity.ok().body(buscarEventoService.buscarEventos(numPagina, tamPagina));
        
    }
    
    @GetMapping("/api/events/{id}")
    public ResponseEntity<Optional<GestaoEventos>> buscarEventosDetalhado(@PathVariable Long id) {
        return ResponseEntity.ok().body(buscarEventoService.buscarEventosDetalhado(id));
    }
    
    @PostMapping("/api/events")
    public ResponseEntity<GestaoEventos> salvarEvento(@RequestBody GestaoEventos gestaoEventos) {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(salvarEventoService.salvarEvento(gestaoEventos));
        
    }
    
    @DeleteMapping("/api/events/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long id) {
        excluirEventoService.excluirEvento(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/api/events/{id}")
    public ResponseEntity<GestaoEventos> atualizarEvento(@PathVariable Long id, @RequestBody @Valid AtualizarEventoDTO dto,
                                                         HttpServletRequest request) {
        return ResponseEntity.ok().body(salvarEventoService.atualizarEvento(id, dto));
        
    }
    
}
