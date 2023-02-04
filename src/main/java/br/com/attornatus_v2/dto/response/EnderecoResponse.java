package br.com.attornatus_v2.dto.response;

import br.com.attornatus_v2.model.Endereco;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class EnderecoResponse {

    private Long id;
    private String logradouro;
    private String CEP;
    private int numero;
    private String cidade;
    private boolean principal;

    public EnderecoResponse() {
    }

    public static Endereco convertToEntity(EnderecoResponse enderecoResponse) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoResponse, endereco);
        return endereco;
    }

    public static List<Endereco> convertToEntity(List<EnderecoResponse> enderecoResponse) {
        Endereco endereco = new Endereco();
        List<Endereco> enderecos = Arrays.asList(endereco);
        enderecoResponse.forEach(enderecoResponse1 -> {
            BeanUtils.copyProperties(enderecoResponse1, endereco);
            enderecos.add(endereco);
        });
        return enderecos;
    }


    public static EnderecoResponse convertToDTO(Endereco endereco) {
        EnderecoResponse enderecoResponse = new EnderecoResponse();
        BeanUtils.copyProperties(endereco, enderecoResponse);
        return enderecoResponse;
    }

    public static Set<EnderecoResponse> convertToDTO(Endereco... endereco) {
        EnderecoResponse enderecoResponse = new EnderecoResponse();
        Set<EnderecoResponse> enderecoResponses = new HashSet<>();
        Arrays.stream(endereco).forEach(endereco1 -> {
            BeanUtils.copyProperties(endereco1, enderecoResponse);
            enderecoResponses.add(enderecoResponse);
        });
        return enderecoResponses;
    }

    public static List<EnderecoResponse> convertToDTO(List<Endereco> endereco) {
        EnderecoResponse enderecoResponse = new EnderecoResponse();
        List<EnderecoResponse> enderecoResponses = Arrays.asList(enderecoResponse);
        endereco.forEach(endereco1 -> {
            BeanUtils.copyProperties(endereco1, enderecoResponse);
            enderecoResponses.add(enderecoResponse);
        });
        return enderecoResponses;
    }


}
