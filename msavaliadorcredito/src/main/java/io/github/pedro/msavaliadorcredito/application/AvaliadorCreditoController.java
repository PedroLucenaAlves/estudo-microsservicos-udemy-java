package io.github.pedro.msavaliadorcredito.application;

import io.github.pedro.msavaliadorcredito.application.exception.DadosClienteNotFoundException;
import io.github.pedro.msavaliadorcredito.application.exception.ErroComunicacaoMicroserviceException;
import io.github.pedro.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.github.pedro.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.github.pedro.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.pedro.msavaliadorcredito.service.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "OK";
    }

    //http://localhost:8080/avaliacoes-credito/situacao-cliente?cpf=(valor)
    @GetMapping(value = "situacao-cliente", params = "cpf") //url + parametro
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {

        //recebe o cpf, consulta o msclientes para consultar os dados e depois consulta o mscartoes
        try {
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build(); //cpf incompleto ou invalido
        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity realizaAvaliacao(@RequestBody DadosAvaliacao dados) {

        try {
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
                    .realizarAvaliacaoCliente(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build(); //cpf incompleto ou invalido
        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

}
