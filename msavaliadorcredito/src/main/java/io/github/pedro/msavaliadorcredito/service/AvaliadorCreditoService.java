package io.github.pedro.msavaliadorcredito.service;

import io.github.pedro.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.pedro.msavaliadorcredito.domain.model.DadosCliente;
import io.github.pedro.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.pedro.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.pedro.msavaliadorcredito.infra.clients.ClientResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClientResourceClient clientResource;

    private final CartoesResourceClient cartoesResourceClient;

    //O objetivo deste metodo é obterDadosClientes - MSCLIENTES e obterDadosCartoes -MSCARTOES
    public SituacaoCliente obterSituacaoCliente(String cpf) {

        ResponseEntity<DadosCliente> dadosClienteResponse = clientResource.dadosCliente(cpf);
        ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartoesResourceClient.getCartoesByCliente(cpf);

        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponse.getBody()) //retorna as info de dadosCliente no body
                .cartoes(dadosCartaoResponse.getBody()) //retorna as info de dadosCartao no body
                .build();
    }

    //TESTE PARA VERIFICAÇÃO DO VALOR QUE CHEGA DO MSCLIENTE
    // Teste 1: A injeção funcionou?
//            if (clientResource == null) {
//        throw new RuntimeException("ERRO GRAVE: clientResource não foi injetado (está nulo)!");
//    }
//
//    ResponseEntity<DadosCliente> dadosResponse = clientResource.dadosCliente(cpf);
//
//    // Teste 2: O Feign devolveu algo?
//            if (dadosResponse == null || dadosResponse.getBody() == null) {
//        throw new RuntimeException("ERRO: O MsClientes respondeu, mas o corpo veio vazio/nulo.");
//    }

}
