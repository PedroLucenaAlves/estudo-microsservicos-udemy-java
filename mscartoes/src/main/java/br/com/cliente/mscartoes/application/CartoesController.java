package br.com.cliente.mscartoes.application;

import br.com.cliente.mscartoes.application.dto.CartaSaveRequest;
import br.com.cliente.mscartoes.domain.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //cartoes que permitam rendas de 5k para baixo serao listados
    //cartoes?renda=5000 ele cai aqui dentro (passando o parametro) - evitar erro de Ambiguous mapping
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> list =  service.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

}
