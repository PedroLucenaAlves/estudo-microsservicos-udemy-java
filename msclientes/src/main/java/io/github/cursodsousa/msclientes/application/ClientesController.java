package io.github.cursodsousa.msclientes.application;

import io.github.cursodsousa.msclientes.application.dto.ClienteRequestDTO;
import io.github.cursodsousa.msclientes.domain.Cliente;
import io.github.cursodsousa.msclientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor //evita utilizar autowired pois ja cria um construtor
@Slf4j
public class ClientesController {

    private final ClienteService clienteService;

    //retorna status verificando se entrou na app ou nao
    @GetMapping
    public String status(){
        log.info("Obtendo o status do microservice de clientes"); //log para visualizar a magica do load balancer
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ClienteRequestDTO requestDTO){
        var cliente = requestDTO.toModel();
        clienteService.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder //constroi url dinamica
                .fromCurrentRequest()
                .query("cpf={cpf}") //serve para passar parametros na url (http://localhost:8080/clientes?cpf={cpf}
                .buildAndExpand(cliente.getCpf()) //constroi a url com o parametro
                .toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dadosCliente(@RequestParam("cpf") String cpf){
        var cliente = clienteService.getByCPF(cpf);
        if (cliente.isEmpty()){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente.get());
    }

}
