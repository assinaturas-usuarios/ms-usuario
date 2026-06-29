package com.empresa.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação de gerenciamento de usuários.
 */
@SpringBootApplication
public class UsuarioApplication {

  /**
   * Ponto de entrada da aplicação.
   *
   * @param args argumentos de linha de comando
   */
  public static void main(String[] args) {
    SpringApplication.run(UsuarioApplication.class, args);
  }
}
