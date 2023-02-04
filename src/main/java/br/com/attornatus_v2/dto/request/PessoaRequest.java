package br.com.attornatus_v2.dto.request;

import br.com.attornatus_v2.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequest {
    private String nome;
    private LocalDate dataNascimento;

    public static Pessoa convertToEntity(PessoaRequest pessoaRequest) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaRequest, pessoa);
        return pessoa;
    }

    public static PessoaRequest convertToDTO(Pessoa pessoa) {
        PessoaRequest pessoaRequest = new PessoaRequest();
        BeanUtils.copyProperties(pessoa, pessoaRequest);
        return pessoaRequest;
    }

}
