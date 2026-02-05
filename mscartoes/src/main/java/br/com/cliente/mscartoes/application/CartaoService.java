package br.com.cliente.mscartoes.application;

import br.com.cliente.mscartoes.domain.Cartao;
import br.com.cliente.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor //gera construtor com a dependecia (repository) injetando a dependencia
public class CartaoService {

    private final CartaoRepository cartaoRepository;

   @Transactional
   public Cartao save(Cartao cartao){
       return cartaoRepository.save(cartao);
   }

   public List<Cartao> getCartoesRendaMenorIgual (Long renda){
       var rendaBigDecial = BigDecimal.valueOf(renda); //converte o valor inteiro recebido em BigDecimal
       return cartaoRepository.findByRendaLessThanEqual(rendaBigDecial);
   }

}
