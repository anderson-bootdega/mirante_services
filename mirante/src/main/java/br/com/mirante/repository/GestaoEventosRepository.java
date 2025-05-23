package br.com.mirante.repository;

import br.com.mirante.domain.GestaoEventos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GestaoEventosRepository extends JpaRepository<GestaoEventos, Long> {
}
