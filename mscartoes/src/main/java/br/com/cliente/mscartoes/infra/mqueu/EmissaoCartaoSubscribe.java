package br.com.cliente.mscartoes.infra.mqueu;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubscribe {

    //conectando com o rabbitmq (nossa fila)

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}") //seta qual fila ele deve escutar
    public void receberSolicitacaoEmissao(@Payload String payload){
        System.out.println(payload);
    }

}
