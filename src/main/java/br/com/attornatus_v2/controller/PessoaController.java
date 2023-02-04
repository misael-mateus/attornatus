package br.com.attornatus_v2.controller;

import br.com.attornatus_v2.dto.request.EnderecoRequest;
import br.com.attornatus_v2.dto.request.PessoaRequest;
import br.com.attornatus_v2.dto.response.EnderecoResponse;
import br.com.attornatus_v2.dto.response.PessoaResponse;
import br.com.attornatus_v2.infra.exception.EnderecoNaoEncontradoException;
import br.com.attornatus_v2.infra.exception.PessoaNaoEncontradaException;
import br.com.attornatus_v2.model.Endereco;
import br.com.attornatus_v2.model.Pessoa;
import br.com.attornatus_v2.service.EnderecoServiceImpl;
import br.com.attornatus_v2.service.PessoaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaServiceImpl pessoaServiceImpl;
    private final EnderecoServiceImpl enderecoServiceImpl;

    public PessoaController(PessoaServiceImpl pessoaServiceImpl, EnderecoServiceImpl enderecoServiceImpl) {
        this.pessoaServiceImpl = pessoaServiceImpl;
        this.enderecoServiceImpl = enderecoServiceImpl;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> createPessoa(@RequestBody PessoaRequest pessoaRequest) {
        Pessoa pessoaEntity = PessoaRequest.convertToEntity(pessoaRequest);
        PessoaResponse response = pessoaServiceImpl.save(pessoaEntity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    @PutMapping("/{idPessoa}")
    public ResponseEntity<PessoaResponse> updatePessoa(@PathVariable Long idPessoa, @RequestBody PessoaRequest pessoa) throws PessoaNaoEncontradaException {
        PessoaResponse response = pessoaServiceImpl.update(idPessoa, pessoa);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> getPessoa(@PathVariable Long id) throws PessoaNaoEncontradaException {
        PessoaResponse response = PessoaResponse.convertToDTO(pessoaServiceImpl.findById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PessoaResponse>> getAllPessoas(Pageable pageable) {
        Page<PessoaResponse> response = pessoaServiceImpl.findAll(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    @PostMapping("/{pessoaId}/enderecos")
    public ResponseEntity<EnderecoResponse> createEndereco(@PathVariable Long pessoaId, @RequestBody EnderecoRequest enderecoRequest) throws PessoaNaoEncontradaException {
        EnderecoResponse response = enderecoServiceImpl.save(pessoaId, enderecoRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{pessoaId}/enderecos")
    public ResponseEntity<Page<EnderecoResponse>> getAllEnderecosByPessoaId(@PathVariable Long pessoaId, Pageable pageable) throws PessoaNaoEncontradaException {
        Page<EnderecoResponse> response = enderecoServiceImpl.findAllByPessoaId(pessoaId, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler({PessoaNaoEncontradaException.class, EnderecoNaoEncontradoException.class})
    @PutMapping("/{pessoaId}/endereco/{enderecoId}/principal")
    public ResponseEntity<EnderecoResponse> setMainAddress(@PathVariable("pessoaId") Long id, @PathVariable("enderecoId") Long addressId) throws PessoaNaoEncontradaException, EnderecoNaoEncontradoException {
        Pessoa pessoa = pessoaServiceImpl.findById(id);
        Endereco endereco = enderecoServiceImpl.findById(addressId);
        if (!endereco.getPessoa().getId().equals(pessoa.getId())) {
            throw new EnderecoNaoEncontradoException("Endereço com id " + addressId + " não pertence a pessoa com id " + id);
        }
        enderecoServiceImpl.setMainAddress(endereco.getId(), pessoa.getId());
        return ResponseEntity.ok(EnderecoResponse.convertToDTO(endereco));
    }

}
