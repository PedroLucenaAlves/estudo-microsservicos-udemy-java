package io.github.pedro.msavaliadorcredito.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Transformando as mensagens em um JSON
 */

@Configuration
public class MqConfig {

    //criacao da fila para onde enviaremos as mensagens
    @Value("${mq.queues.emissao-cartoes}")
    private String emissaoCartoesFila;

    public Queue queueEmissaoCartoes(){
        return new Queue(emissaoCartoesFila, true);
    }

}
