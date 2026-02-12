package io.github.pedro.msavaliadorcredito.service;

import feign.FeignException;
import io.github.pedro.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.pedro.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import io.github.pedro.msavaliadorcredito.domain.model.*;
import io.github.pedro.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.pedro.msavaliadorcredito.infra.clients.ClientResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClientResourceClient clientResource;

    private final CartoesResourceClient cartoesResourceClient;

    //O objetivo deste metodo é obterDadosClientes - MSCLIENTES e obterDadosCartoes -MSCARTOES
    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException,
            ErroComunicacaoMicroserviceException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientResource.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartoesResourceClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponse.getBody()) //retorna as info de dadosCliente no body
                    .cartoes(dadosCartaoResponse.getBody()) //retorna as info de dadosCartao no body
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
    }

    //metodo para fazer avaliacao para o cliente
    public RetornoAvaliacaoCliente realizarAvaliacaoCliente(String cpf, Long renda)
    throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException{
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientResource.dadosCliente(cpf); //vai ao msclientes e obtem os dados do cliente
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesResourceClient.getCartoesRendaAte(renda); //renda ate onde o cliente especificou

            List<Cartao> cartoes = cartoesResponse.getBody(); //quantidade de cartoes que o cliente pode ter
           //mapeamento do cartao
            var listaCartoesAprovados = cartoes.stream().map(

                    cartao -> {

                        DadosCliente dadosCliente = dadosClienteResponse.getBody();

                        BigDecimal limiteBasico = cartao.getLimiteBasico();
                        BigDecimal rendaDB = BigDecimal.valueOf(renda);
                        BigDecimal idadeDB = BigDecimal.valueOf(dadosCliente.getIdade());
                        var fator =  idadeDB.divide(BigDecimal.valueOf(10));
                        BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                        CartaoAprovado aprovado = new CartaoAprovado();
                        aprovado.setCartao(cartao.getNome());
                        aprovado.setBandeira(cartao.getBandeira());
                        aprovado.setLimiteAprovado(limiteAprovado);

                        return aprovado;
                    }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
        }
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
