package br.com.attornatus_v2.repository;

import br.com.attornatus_v2.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
