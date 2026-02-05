package br.com.cliente.mscartoes.application;

import br.com.cliente.mscartoes.domain.ClienteCartao;
import br.com.cliente.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //constroi um construtor com argumentos obrigatorios
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;

    public List<ClienteCartao> listCartoesByCpf(String cpf){
        return repository.findByCpf(cpf);
    }

}
