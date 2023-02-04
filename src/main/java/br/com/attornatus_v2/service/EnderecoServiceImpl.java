package br.com.attornatus_v2.service;

import br.com.attornatus_v2.dto.request.EnderecoRequest;
import br.com.attornatus_v2.dto.response.EnderecoResponse;
import br.com.attornatus_v2.infra.exception.EnderecoNaoEncontradoException;
import br.com.attornatus_v2.model.Endereco;
import br.com.attornatus_v2.model.Pessoa;
import br.com.attornatus_v2.repository.EnderecoRepository;
import br.com.attornatus_v2.service.strategy.EnderecoService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class EnderecoServiceImpl implements EnderecoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final PessoaServiceImpl pessoaServiceImpl;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, PessoaServiceImpl pessoaServiceImpl) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaServiceImpl = pessoaServiceImpl;
    }

    @Transactional
    public EnderecoResponse save(Long idPessoa, EnderecoRequest enderecoRequest) {
        LOGGER.info("Iniciando método save com id da pessoa: {}", idPessoa);
        Pessoa pessoa = pessoaServiceImpl.findById(idPessoa);
        Endereco endereco = EnderecoRequest.convertToEntity(enderecoRequest);
        endereco.setPessoa(pessoa);
        EnderecoResponse enderecoResponse = EnderecoResponse.convertToDTO(this.enderecoRepository.save(endereco));
        LOGGER.info("Finalizando método save com resultado: {}", enderecoResponse);
        return enderecoResponse;
    }

    @Transactional
    public EnderecoResponse save(EnderecoRequest enderecoRequest) {
        LOGGER.info("Iniciando método save sem id da pessoa");
        Endereco endereco = EnderecoRequest.convertToEntity(enderecoRequest);
        endereco.setPrincipal(false);
        EnderecoResponse enderecoResponse = EnderecoResponse.convertToDTO(enderecoRepository.save(endereco));
        LOGGER.info("Finalizando método save sem id da pessoa com resultado: {}", enderecoResponse);
        return enderecoResponse;
    }

    public Endereco findById(Long id) throws EnderecoNaoEncontradoException {
        LOGGER.info("Iniciando método findById com id: {}", id);
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço com id " + id + " não encontrado"));
        LOGGER.info("Finalizando método findById com resultado: {}", endereco);
        return endereco;
    }

    public Page<EnderecoResponse> findAllByPessoaId(Long pessoaId, Pageable pageable) {
        Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);
        logger.info("Buscando todos os endereços da pessoa com id: {}", pessoaId);

        List<Endereco> enderecos = enderecoRepository.findAllByPessoaId(pessoaId);

        logger.info("Quantidade de endereços encontrados: {}", enderecos.size());
        return PageableExecutionUtils.getPage(enderecos, pageable, () -> enderecoRepository.countAllByPessoaId(pessoaId))
                .map(EnderecoResponse::convertToDTO);
    }

    public void delete(Long id) throws EnderecoNaoEncontradoException {
        Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);
        logger.info("Deletando endereço com id: {}", id);

        Endereco endereco = findById(id);
        enderecoRepository.delete(endereco);

        logger.info("Endereço com id {} deletado com sucesso", id);
    }

    @Transactional
    public EnderecoResponse setMainAddress(Long idEndereco, Long idPessoa) {
        Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);
        logger.info("Definindo endereço com id {} como principal para pessoa com id {}", idEndereco, idPessoa);

        Endereco endereco = findById(idEndereco);
        Pessoa pessoa = pessoaServiceImpl.findById(idPessoa);
        clearMainAddress(pessoa);
        endereco.setPrincipal(true);
        EnderecoResponse enderecoResponse = EnderecoResponse.convertToDTO(enderecoRepository.save(endereco));

        logger.info("Endereço com id {} definido como principal para pessoa com id {}", idEndereco, idPessoa);
        return enderecoResponse;
    }

    @Transactional
    public void clearMainAddress(Pessoa pessoa) {
        Logger logger = LoggerFactory.getLogger(EnderecoServiceImpl.class);
        logger.info("Removendo endereço principal da pessoa com id: {}", pessoa.getId());

        pessoa.getEndereco().forEach(endereco -> endereco.setPrincipal(false));

        logger.info("Endereço principal removido da pessoa com id: {}", pessoa.getId());
    }

}
