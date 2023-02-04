package br.com.attornatus_v2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "endereco")
@Table(name = "enderecos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 100)
    private String logradouro;
    @NotNull
    @Size(min = 8, max = 8)
    private String CEP;
    @NotNull
    @Min(1)
    @Max(99999)
    private int numero;
    @NotNull
    @Size(min = 3, max = 100)
    private String cidade;
    @JsonIgnore
    @ManyToOne
    private Pessoa pessoa;
    @NotNull
    private boolean principal;
}
