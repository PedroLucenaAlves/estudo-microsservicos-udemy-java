package io.github.pedro.mscloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class MscloudgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscloudgatewayApplication.class, args);
	}

	//o gateway funciona como um balanceador de cargas, entao independente das portas dos ms, todas responderam
	//a requisicao que eu fizer ao gateway
	//exp: se meu msclientes esta na porta 3560 e o mscartoes na 3578 e o gateway na 8080
	//toda req que eu fizer nos endpoints dos ms com a porta 8080 (gateway) sera atendida por causa do Load Balancer
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route(r -> r.path("/clientes/**").uri("lb://msclientes")) //toda request para /clientes redireciona para o load balancer e para o msclientes
				.route(r -> r.path("/cartoes/**").uri("lb://mscartoes")) //toda request para /clientes redireciona para o load balancer e para o mscartoes
				.build();
	}

}
