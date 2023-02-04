package br.com.attornatus_v2.dto.request;

import br.com.attornatus_v2.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequest {
    private String logradouro;
    private String CEP;
    private int numero;
    private String cidade;
    public static Endereco convertToEntity(EnderecoRequest enderecoRequest) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequest, endereco);
        return endereco;
    }

    public static EnderecoRequest convertToDTO(Endereco endereco) {
        EnderecoRequest enderecoRequest = new EnderecoRequest();
        BeanUtils.copyProperties(endereco, enderecoRequest);
        return enderecoRequest;
    }
}
