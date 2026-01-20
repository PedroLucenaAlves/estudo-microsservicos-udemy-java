package io.github.cursodsousa.msclientes.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clientes")
public class ClientesController {

    //retorna status verificando se entrou na app ou nao
    @GetMapping
    public String status(){
        return "ok";
    }


}
