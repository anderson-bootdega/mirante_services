package br.com.mirante.service;

import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.domain.exception.BusinessException;
import br.com.mirante.repository.GestaoEventosRepository;
import org.springframework.stereotype.Service;

@Service
public class ExcluirEventoService {
    
    private final GestaoEventosRepository gestaoEventosRepository;
    
    ExcluirEventoService(GestaoEventosRepository gestaoEventosRepository){
        this.gestaoEventosRepository = gestaoEventosRepository;
    }
    
    public void excluirEvento(Long id) {
        GestaoEventos evento = gestaoEventosRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Evento com ID " + id + " não encontrado."));
        System.out.println("eventos"+evento);
        if (Boolean.TRUE.equals(evento.getDeleted())) {
            throw new BusinessException("O evento já está marcado como excluído.");
        }
        
        evento.setDeleted(true);
      
        gestaoEventosRepository.save(evento);
    }
    
}
