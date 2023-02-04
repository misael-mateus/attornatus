package br.com.attornatus_v2.service.strategy;

import br.com.attornatus_v2.dto.request.PessoaRequest;
import br.com.attornatus_v2.dto.response.PessoaResponse;
import br.com.attornatus_v2.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaService {

    public PessoaResponse save(Pessoa pessoa);

    public PessoaResponse update(Long idPessoa,PessoaRequest pessoa);

    public Pessoa findById(Long id);

    public Page<PessoaResponse> findAll(Pageable pageable);

    public void deleteById(Long id);

}
