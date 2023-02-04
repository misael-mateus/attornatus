package br.com.attornatus_v2.service.strategy;

import br.com.attornatus_v2.dto.request.EnderecoRequest;
import br.com.attornatus_v2.dto.response.EnderecoResponse;
import br.com.attornatus_v2.model.Endereco;
import br.com.attornatus_v2.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoService {
    public EnderecoResponse save(Long idPessoa, EnderecoRequest endereco);

    public EnderecoResponse save(EnderecoRequest endereco);

    public Endereco findById(Long id);

    public Page<EnderecoResponse> findAllByPessoaId(Long pessoaId, Pageable pageable);

    public void delete(Long id);

    public EnderecoResponse setMainAddress(Long i, Long idPessoa);

    public void clearMainAddress(Pessoa pessoa);
}
