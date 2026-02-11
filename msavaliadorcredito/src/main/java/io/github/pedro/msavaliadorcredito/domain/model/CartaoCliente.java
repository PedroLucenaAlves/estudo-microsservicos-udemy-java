package io.github.pedro.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Classe que ira representar o retorno dos cartoes do cliente (response do mscartoes)
 */

@Data
public class CartaoCliente {

    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

}
