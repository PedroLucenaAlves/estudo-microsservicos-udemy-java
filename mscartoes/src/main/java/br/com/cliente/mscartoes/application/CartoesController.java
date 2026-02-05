package br.com.cliente.mscartoes.application;

import br.com.cliente.mscartoes.application.dto.CartaSaveRequest;
import br.com.cliente.mscartoes.domain.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cartoes")
@AllArgsConstructor
public class CartoesController {

    private final CartaoService service;


    @GetMapping
    public String status(){
        return "MSCARTOES - OK";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaSaveRequest request){
        Cartao cartao = request.toModel();
        service.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
