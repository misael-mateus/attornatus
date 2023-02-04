package br.com.attornatus_v2;

import br.com.attornatus_v2.controller.PessoaController;
import br.com.attornatus_v2.dto.request.EnderecoRequest;
import br.com.attornatus_v2.dto.request.PessoaRequest;
import br.com.attornatus_v2.dto.response.EnderecoResponse;
import br.com.attornatus_v2.dto.response.PessoaResponse;
import br.com.attornatus_v2.infra.exception.PessoaNaoEncontradaException;
import br.com.attornatus_v2.model.Endereco;
import br.com.attornatus_v2.model.Pessoa;
import br.com.attornatus_v2.service.EnderecoServiceImpl;
import br.com.attornatus_v2.service.PessoaServiceImpl;
import br.com.attornatus_v2.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ControllerPessoaTest {

    @Mock
    private PessoaServiceImpl pessoaServiceImpl;
    @Mock
    private EnderecoServiceImpl enderecoServiceImpl;
    @InjectMocks
    private PessoaController pessoaController;


    @Test
    void testCreatePessoaSuccess() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest("test name", LocalDate.of(1990, 1, 1));
        Pessoa pessoaEntity = PessoaRequest.convertToEntity(pessoaRequest);
        PessoaResponse pessoaResponse = new PessoaResponse();
        pessoaResponse.setId(1L);
        pessoaResponse.setNome(pessoaRequest.getNome());
        when(pessoaServiceImpl.save(pessoaEntity)).thenReturn(pessoaResponse);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(pessoaRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePessoaSuccess() throws PessoaNaoEncontradaException {
        Long id = 1L;
        PessoaRequest pessoaRequest = new PessoaRequest("test name", LocalDate.of(1990, 1, 1));
        Pessoa pessoa = new Pessoa(1L, "test name", LocalDate.of(1990, 1, 1), null);
        PessoaResponse expectedResponse = new PessoaResponse(1L, "test name", LocalDate.of(1990, 1, 1), null);

        when(pessoaServiceImpl.update(id, pessoaRequest)).thenReturn(expectedResponse);
        ResponseEntity<PessoaResponse> responseEntity = pessoaController.updatePessoa(id, pessoaRequest);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(id);
        verify(pessoaServiceImpl).update(id, pessoaRequest);
    }


    @Test
    void testUpdatePessoaNotFound() {
        Long idPessoa = 1L;
        PessoaRequest pessoaRequest = new PessoaRequest("test name", LocalDate.of(1990, 1, 1));
        doThrow(PessoaNaoEncontradaException.class).when(pessoaServiceImpl).update(idPessoa, pessoaRequest);
        Exception exception = assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaController.updatePessoa(idPessoa, pessoaRequest));
        verify(pessoaServiceImpl).update(idPessoa, pessoaRequest);
    }

    @Test
    void testGetPessoaSucess() throws PessoaNaoEncontradaException {
        Long id = 1L;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNome("Pessoa Teste");
        when(pessoaServiceImpl.findById(id)).thenReturn(pessoa);
        ResponseEntity<PessoaResponse> response = pessoaController.getPessoa(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(id);
        assertThat(response.getBody().getNome()).isEqualTo("Pessoa Teste");
        verify(pessoaServiceImpl, times(1)).findById(id);
    }

    @Test
    void testGetPessoaNotFound() throws PessoaNaoEncontradaException {
        Long idPessoa = 1L;
        doThrow(PessoaNaoEncontradaException.class).when(pessoaServiceImpl).findById(idPessoa);
        Exception exception = assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaController.getPessoa(idPessoa));
        verify(pessoaServiceImpl).findById(idPessoa);
    }

    @Test
    void testCreateEnderecoSuccess() throws Exception {
        Long idPessoa = 1L;
        EnderecoRequest enderecoRequest = new EnderecoRequest("Rua Teste", "123", 50, "anapolis");
        Endereco endereco = EnderecoRequest.convertToEntity(enderecoRequest);
        EnderecoResponse enderecoResponse = new EnderecoResponse();
        enderecoResponse.setId(1L);
        enderecoResponse.setLogradouro(enderecoRequest.getLogradouro());
        enderecoResponse.setNumero(enderecoRequest.getNumero());
        enderecoResponse.setCidade(enderecoRequest.getCidade());
        enderecoResponse.setCEP(enderecoRequest.getCEP());
        when(enderecoServiceImpl.save(idPessoa, enderecoRequest)).thenReturn(enderecoResponse);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(pessoaController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/" + idPessoa + "/enderecos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.asJsonString(enderecoRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateEnderecoNotFound() throws Exception {
        Long idPessoa = 1L;
        EnderecoRequest enderecoRequest = new EnderecoRequest("Rua Teste", "123", 50, "anapolis");
        doThrow(PessoaNaoEncontradaException.class).when(enderecoServiceImpl).save(idPessoa, enderecoRequest);
        Exception exception = assertThrows(PessoaNaoEncontradaException.class,
                () -> pessoaController.createEndereco(idPessoa, enderecoRequest));
        verify(enderecoServiceImpl).save(idPessoa, enderecoRequest);
    }

}
