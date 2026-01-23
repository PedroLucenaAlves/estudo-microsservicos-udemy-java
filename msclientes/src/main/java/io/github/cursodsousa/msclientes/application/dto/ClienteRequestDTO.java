package io.github.cursodsousa.msclientes.application.dto;

import io.github.cursodsousa.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(cpf, nome, idade);
    }

}
