package com.empresa.usuario.infrastructure.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.empresa.usuario.infrastructure.exception.EmailJaCadastradoException;
import com.empresa.usuario.infrastructure.exception.UsuarioNaoEncontradoException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("GlobalExceptionHandler - ms-usuario")
class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;

  @Mock
  private MethodArgumentNotValidException validacaoException;

  @Mock
  private BindingResult bindingResult;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("Deve retornar 404 para UsuarioNaoEncontradoException")
  void deveRetornar404ParaUsuarioNaoEncontrado() {
    UsuarioNaoEncontradoException ex = new UsuarioNaoEncontradoException(UUID.randomUUID());
    ProblemDetail result = handler.handleNaoEncontrado(ex);
    assertThat(result.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  @DisplayName("Deve retornar 409 para EmailJaCadastradoException")
  void deveRetornar409ParaEmailDuplicado() {
    EmailJaCadastradoException ex = new EmailJaCadastradoException("email@test.com");
    ProblemDetail result = handler.handleEmailDuplicado(ex);
    assertThat(result.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
  }

  @Test
  @DisplayName("Deve retornar 422 para MethodArgumentNotValidException com campos")
  void deveRetornar422ComCamposDeValidacao() {
    FieldError fieldError = new FieldError("usuario", "email", "Email invalido");
    when(validacaoException.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
    ProblemDetail result = handler.handleValidacao(validacaoException);
    assertThat(result.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
  }

  @Test
  @DisplayName("Deve retornar 500 para Exception generica")
  void deveRetornar500ParaExcecaoGenerica() {
    Exception ex = new RuntimeException("erro inesperado");
    ProblemDetail result = handler.handleGenerico(ex);
    assertThat(result.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }
}