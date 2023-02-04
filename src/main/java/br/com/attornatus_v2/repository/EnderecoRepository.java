package br.com.attornatus_v2.repository;

import br.com.attornatus_v2.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findAllByPessoaId(Long pessoaId);

    long countAllByPessoaId(Long pessoaId);
}
