package br.com.mirante.repository;

import br.com.mirante.domain.GestaoEventos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GestaoEventosRepository extends JpaRepository<GestaoEventos, Long> {
    Page<GestaoEventos> findByDeletedFalse(Pageable pageable);
    
    Optional<GestaoEventos> findByIdAndDeletedFalse(Long id);
    
}
