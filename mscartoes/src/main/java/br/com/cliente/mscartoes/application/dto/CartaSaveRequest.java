package br.com.cliente.mscartoes.application.dto;

import br.com.cliente.mscartoes.domain.BandeiraCartao;
import br.com.cliente.mscartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limte;

    //TRANSFORMA O DTO EM UMA ENTIDADE/objeto tipo CARTAO
    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limte);
    }

}
