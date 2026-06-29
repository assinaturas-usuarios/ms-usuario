package com.empresa.usuario.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.domain.port.out.UsuarioRepositoryPort;
import com.empresa.usuario.infrastructure.exception.UsuarioNaoEncontradoException;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioUseCaseImplTest {

  @Mock
  private UsuarioRepositoryPort repository;

  @Mock
  private UsuarioMapper mapper;

  @InjectMocks
  private BuscarUsuarioUseCaseImpl useCase;

  @Nested
  @DisplayName("Cenários de sucesso")
  class CenariosSuccesso {

    @Test
    @DisplayName("Deve retornar usuário quando encontrado")
    void deveRetornarUsuario() {
      UUID id = UUID.randomUUID();
      Usuario usuario = new Usuario(id, "Maria", "maria@email.com", LocalDateTime.now());
      UsuarioResponse response = new UsuarioResponse(id, "Maria", "maria@email.com",
          usuario.getDataCadastro());

      given(repository.buscarPorId(id)).willReturn(Optional.of(usuario));
      given(mapper.toResponse(usuario)).willReturn(response);

      UsuarioResponse resultado = useCase.buscarPorId(id);

      assertThat(resultado.id()).isEqualTo(id);
      assertThat(resultado.email()).isEqualTo("maria@email.com");
    }
  }

  @Nested
  @DisplayName("Cenários de falha")
  class CenariosFailha {

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void deveLancarExcecaoNaoEncontrado() {
      UUID id = UUID.randomUUID();
      given(repository.buscarPorId(id)).willReturn(Optional.empty());

      assertThatThrownBy(() -> useCase.buscarPorId(id))
          .isInstanceOf(UsuarioNaoEncontradoException.class)
          .hasMessageContaining(id.toString());
    }
  }
}
