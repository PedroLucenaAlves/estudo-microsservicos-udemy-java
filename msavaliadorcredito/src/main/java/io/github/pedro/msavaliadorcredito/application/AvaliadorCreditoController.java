package io.github.pedro.msavaliadorcredito.application;

import io.github.pedro.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.pedro.msavaliadorcredito.service.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "OK";
    }

    //http://localhost:8080/avaliacoes-credito/situacao-cliente?cpf=(valor)
    @GetMapping(value="situacao-cliente", params ="cpf") //url + parametro
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
        //recebe o cpf, consulta o msclientes para consultar os dados e depois consulta o mscartoes
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        return ResponseEntity.ok(situacaoCliente);
    }

}
