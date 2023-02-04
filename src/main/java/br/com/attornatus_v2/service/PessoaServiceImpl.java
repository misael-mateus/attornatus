package br.com.attornatus_v2.service;

import br.com.attornatus_v2.dto.request.PessoaRequest;
import br.com.attornatus_v2.dto.response.PessoaResponse;
import br.com.attornatus_v2.infra.exception.PessoaNaoEncontradaException;
import br.com.attornatus_v2.model.Pessoa;
import br.com.attornatus_v2.repository.PessoaRepository;
import br.com.attornatus_v2.service.strategy.PessoaService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PessoaServiceImpl implements PessoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaServiceImpl.class);

    private final PessoaRepository pessoaRepository;


    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public PessoaResponse save(Pessoa pessoa) {
        LOGGER.info("Salvando uma pessoa");
        return PessoaResponse.convertToDTO(pessoaRepository.save(pessoa));
    }

    @Transactional
    public PessoaResponse update(Long idPessoa, PessoaRequest pessoa) throws PessoaNaoEncontradaException {
        LOGGER.info("Atualizando uma pessoa com id " + idPessoa);
        Pessoa pessoaToUpdate = findById(idPessoa);
        BeanUtils.copyProperties(pessoa, pessoaToUpdate);
        return PessoaResponse.convertToDTO(pessoaToUpdate);
    }

    public Pessoa findById(Long id) throws PessoaNaoEncontradaException {
        LOGGER.info("Buscando pessoa com id " + id);
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new PessoaNaoEncontradaException("Pessoa com id " + id + " não encontrada"));
    }

    public Page<PessoaResponse> findAll(Pageable pageable) {
        LOGGER.info("Buscando todas as pessoas");
        return pessoaRepository.findAll(pageable).map(PessoaResponse::convertToDTO);
    }

    @Transactional
    public void deleteById(Long id) throws PessoaNaoEncontradaException {
        LOGGER.info("Deletando pessoa com id " + id);
        Pessoa pessoa = findById(id);
        pessoaRepository.delete(pessoa);
    }

}
