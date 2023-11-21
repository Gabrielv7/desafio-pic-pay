package com.gabriel.desafiopicpay.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API PicPay Simplificado",
                description = "Desenvolvi essa API com base em um desafio técnico do picpay que se encontra no repositório " +
                        "do GitHub: https://github.com/PicPay/picpay-desafio-backend",
                version = "1.0.0",
                contact = @Contact(
                        email = "gabrielcosta.v7@gmail.com",
                        name = "Gabriel Costa Afonso"
                )
        ),
        servers = @Server(
                url = "http://localhost:8080",
                description = "Localhost Server URL"
        )
)
public class OpenApi {

}
