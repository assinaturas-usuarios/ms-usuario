package com.empresa.usuario.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.domain.port.out.UsuarioRepositoryPort;
import com.empresa.usuario.infrastructure.exception.EmailJaCadastradoException;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastrarUsuarioUseCaseImplTest {

  @Mock
  private UsuarioRepositoryPort repository;

  @Mock
  private UsuarioMapper mapper;

  @InjectMocks
  private CadastrarUsuarioUseCaseImpl useCase;

  private CadastrarUsuarioRequest request;
  private Usuario usuario;
  private UsuarioResponse response;

  @BeforeEach
  void setUp() {
    request = new CadastrarUsuarioRequest("João Silva", "joao@email.com");
    usuario = new Usuario(UUID.randomUUID(), "João Silva", "joao@email.com", LocalDateTime.now());
    response = new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(),
        usuario.getDataCadastro());
  }

  @Nested
  @DisplayName("Cenários de sucesso")
  class CenariosSuccesso {

    @Test
    @DisplayName("Deve cadastrar usuário quando e-mail não existe")
    void deveCadastrarUsuario() {
      given(repository.existePorEmail(request.email())).willReturn(false);
      given(repository.salvar(any(Usuario.class))).willReturn(usuario);
      given(mapper.toResponse(usuario)).willReturn(response);

      UsuarioResponse resultado = useCase.cadastrar(request);

      assertThat(resultado).isEqualTo(response);
      verify(repository).salvar(any(Usuario.class));
    }
  }

  @Nested
  @DisplayName("Cenários de falha")
  class CenariosFalha {

    @Test
    @DisplayName("Deve lançar exceção quando e-mail já está cadastrado")
    void deveLancarExcecaoEmailDuplicado() {
      given(repository.existePorEmail(request.email())).willReturn(true);

      assertThatThrownBy(() -> useCase.cadastrar(request))
          .isInstanceOf(EmailJaCadastradoException.class)
          .hasMessageContaining("joao@email.com");

      verify(repository, never()).salvar(any());
    }
  }
}
