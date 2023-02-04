package br.com.attornatus_v2.dto.response;

import br.com.attornatus_v2.model.Endereco;
import br.com.attornatus_v2.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Set<Endereco> endereco = new HashSet<>();

    public static PessoaResponse convertToDTO(Pessoa save) {
        PessoaResponse pessoaResponse = new PessoaResponse();
        BeanUtils.copyProperties(save, pessoaResponse);
        return pessoaResponse;
    }

    public static List<PessoaResponse> convertToDTO(Pessoa... save) {
        PessoaResponse pessoaResponse = new PessoaResponse();
        List<PessoaResponse> pessoaResponses = new ArrayList<>(List.of(pessoaResponse));

        Arrays.stream(save).forEach(pessoa -> {
            BeanUtils.copyProperties(pessoa, pessoaResponse);
            pessoaResponses.add(pessoaResponse);
        });

        return pessoaResponses;
    }


}
