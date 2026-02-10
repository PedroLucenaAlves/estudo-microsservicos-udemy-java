package br.com.cliente.mscartoes.application;

import br.com.cliente.mscartoes.application.dto.CartaSaveRequest;
import br.com.cliente.mscartoes.application.dto.CartoesPorClienteResponse;
import br.com.cliente.mscartoes.domain.Cartao;
import br.com.cliente.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@AllArgsConstructor
public class CartoesController {

    private final CartaoService cartaoServiceservice;
    private final ClienteCartaoService clienteCartaoService;


    @GetMapping
    public String status(){
        return "MSCARTOES - OK";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaSaveRequest request){
        Cartao cartao = request.toModel();
        cartaoServiceservice.save(cartao);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //cartoes que permitam rendas de 5k para baixo serao listados
    //cartoes?renda=5000 ele cai aqui dentro (passando o parametro) - evitar erro de Ambiguous mapping
    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> list =  cartaoServiceservice.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(list);
    }

    @GetMapping( params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){
        List<ClienteCartao> lista = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = lista
                .stream()
                .map(CartoesPorClienteResponse::fromModel) //pega cada objeto ClienteCartao e transforma no nosso DTO
                .collect(Collectors.toList()); //armazena os objetos transformados numa lista
        return ResponseEntity.ok(resultList);
    }

}
