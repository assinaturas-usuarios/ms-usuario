package com.empresa.usuario.infrastructure.adapter.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.port.in.BuscarUsuarioUseCase;
import com.empresa.usuario.domain.port.in.CadastrarUsuarioUseCase;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioController")
class UsuarioControllerTest {

  @Mock
  private CadastrarUsuarioUseCase cadastrarUseCase;

  @Mock
  private BuscarUsuarioUseCase buscarUseCase;

  private UsuarioController controller;
  private static final LocalDateTime DATA_FIXA = LocalDateTime.of(2026, 1, 15, 10, 30, 0);

  @BeforeEach
  void setUp() {
    controller = new UsuarioController(cadastrarUseCase, buscarUseCase);
  }

  @Nested
  @DisplayName("cadastrar")
  class Cadastrar {

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    void deveCadastrarComSucesso() {
      UUID usuarioId = UUID.randomUUID();
      CadastrarUsuarioRequest request = new CadastrarUsuarioRequest("Ana Lima", "ana@email.com");
      UsuarioResponse response = new UsuarioResponse(usuarioId, "Ana Lima", "ana@email.com", DATA_FIXA);

      given(cadastrarUseCase.cadastrar(request)).willReturn(response);

      UsuarioResponse result = controller.cadastrar(request);

      assert result.id().equals(usuarioId);
      assert result.nome().equals("Ana Lima");
      assert result.email().equals("ana@email.com");
      verify(cadastrarUseCase).cadastrar(request);
    }
  }

  @Nested
  @DisplayName("buscarPorId")
  class BuscarPorId {

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarPorIdComSucesso() {
      UUID usuarioId = UUID.randomUUID();
      UsuarioResponse response = new UsuarioResponse(usuarioId, "João Silva", "joao@email.com", DATA_FIXA);

      given(buscarUseCase.buscarPorId(usuarioId)).willReturn(response);

      UsuarioResponse result = controller.buscarPorId(usuarioId);

      assert result.id().equals(usuarioId);
      assert result.nome().equals("João Silva");
      assert result.email().equals("joao@email.com");
      verify(buscarUseCase).buscarPorId(usuarioId);
    }
  }
}
