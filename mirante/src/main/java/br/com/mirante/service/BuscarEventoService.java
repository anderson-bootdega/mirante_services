package br.com.mirante.service;

import br.com.mirante.domain.GestaoEventos;

import br.com.mirante.repository.GestaoEventosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuscarEventoService {
   
   private final  GestaoEventosRepository gestaoEventosRepository;
    
    BuscarEventoService(GestaoEventosRepository gestaoEventosRepository){
        this.gestaoEventosRepository = gestaoEventosRepository;
    }
    
   public Page<GestaoEventos> buscarEventos(int numPagina, int tamanhoPagina){
       Pageable pageable = PageRequest.of(numPagina, tamanhoPagina);
       
       Page<GestaoEventos> eventos =  gestaoEventosRepository.findByDeletedFalse(pageable);
       
       if (eventos.isEmpty()) {
           throw new EntityNotFoundException("Nenhum evento encontrado na página " + tamanhoPagina);
       }
       
             return eventos.map(event -> new GestaoEventos(
               event.getId(),
               event.getTitulo(),
               event.getDescricao(),
               event.getDataHora(),
               event.getLocal(),
               event.getDeleted()
       ));
   }
    
    public Optional<GestaoEventos> buscarEventosDetalhado(Long id) {
        return gestaoEventosRepository.findByIdAndDeletedFalse(id);
    }
}
