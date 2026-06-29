package com.empresa.usuario.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger para a documentação da API.
 */
@Configuration
public class SwaggerConfig {

  /**
   * Configura as informações do OpenAPI.
   *
   * @return instância configurada do OpenAPI
   */
  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Usuário API")
            .description("Serviço de gerenciamento de usuários")
            .version("v1"));
  }
}
