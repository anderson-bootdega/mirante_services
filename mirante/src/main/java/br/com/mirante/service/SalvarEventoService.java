package br.com.mirante.service;

import br.com.mirante.domain.AtualizarEventoDTO;
import br.com.mirante.domain.GestaoEventos;
import br.com.mirante.domain.exception.BusinessException;
import br.com.mirante.repository.GestaoEventosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SalvarEventoService {
    
    private final GestaoEventosRepository gestaoEventosRepository;
    
    SalvarEventoService(GestaoEventosRepository gestaoEventosRepository){
        this.gestaoEventosRepository = gestaoEventosRepository;
    }
    
    public GestaoEventos salvarEvento(GestaoEventos evento) {
        try {
            return gestaoEventosRepository.save(evento);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violação de integridade nos dados do evento: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new BusinessException("Erro ao salvar o evento", e);
        }
    }
    
  
    public GestaoEventos atualizarEvento(Long id, AtualizarEventoDTO dto) {
        GestaoEventos evento = gestaoEventosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento com id " + id + " não encontrado"));
        
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setDataHora(dto.dataHora());
        evento.setLocal(dto.local());
        
        return gestaoEventosRepository.save(evento);
    }
    
}
