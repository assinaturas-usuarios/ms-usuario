package com.empresa.usuario.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Usuario - Domain Model")
class UsuarioTest {

  @Nested
  @DisplayName("novo")
  class NovoFactory {

    @Test
    @DisplayName("Deve criar novo usuário com nome e email")
    void deveCriarNovoUsuario() {
      String nome = "João Silva";
      String email = "joao@email.com";

      Usuario usuario = Usuario.novo(nome, email);

      assertThat(usuario.getNome()).isEqualTo(nome);
      assertThat(usuario.getEmail()).isEqualTo(email);
      assertThat(usuario.getDataCadastro()).isNotNull();
    }

    @Test
    @DisplayName("Deve gerar ID nulo para novo usuário")
    void deveGerarIdNuloParaNovoUsuario() {
      Usuario usuario = Usuario.novo("Ana", "ana@email.com");
      assertThat(usuario.getId()).isNull();
    }

    @Test
    @DisplayName("Deve registrar data de cadastro não anterior ao agora")
    void deveRegistrarDataDeCadastro() {
      LocalDateTime antes = LocalDateTime.now();
      Usuario usuario = Usuario.novo("Maria", "maria@email.com");
      LocalDateTime depois = LocalDateTime.now();

      assertThat(usuario.getDataCadastro()).isBetween(antes, depois);
    }
  }

  @Nested
  @DisplayName("Construtor")
  class ConstructorTests {

    @Test
    @DisplayName("Deve criar usuário com todos os parâmetros")
    void deveCriarUsuarioComTodosParametros() {
      UUID id = UUID.randomUUID();
      String nome = "Pedro Costa";
      String email = "pedro@email.com";
      LocalDateTime data = LocalDateTime.of(2026, 1, 15, 10, 30, 0);

      Usuario usuario = new Usuario(id, nome, email, data);

      assertThat(usuario.getId()).isEqualTo(id);
      assertThat(usuario.getNome()).isEqualTo(nome);
      assertThat(usuario.getEmail()).isEqualTo(email);
      assertThat(usuario.getDataCadastro()).isEqualTo(data);
    }
  }
}
