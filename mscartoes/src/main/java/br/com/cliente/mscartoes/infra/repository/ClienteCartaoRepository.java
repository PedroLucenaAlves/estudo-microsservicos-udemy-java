package br.com.cliente.mscartoes.infra.repository;

import br.com.cliente.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

    List<ClienteCartao> findByCpf(String cpf); //faz uma busca na entidade pelo cpf

}
