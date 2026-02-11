package io.github.pedro.msavaliadorcredito.infra.clients;

import io.github.pedro.msavaliadorcredito.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//o feigclient é um client http
//aqui ele busca pelo nome que definimos no yml de cada ms

//nao setamos a url para que ele possa pegar a instancia via load-balancer (api gateway)
@FeignClient(value = "msclientes" , path = "/clientes") //busca as reqs para /clientes
public interface ClientResourceClient {

    //metodo do controller de msclientes
    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
