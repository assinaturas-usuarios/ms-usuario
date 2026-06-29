package com.empresa.usuario.infrastructure.handler;

import com.empresa.usuario.infrastructure.exception.EmailJaCadastradoException;
import com.empresa.usuario.infrastructure.exception.UsuarioNaoEncontradoException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Manipulador global de exceções para a aplicação.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Manipulador de exceção para usuário não encontrado.
   *
   * @param ex exceção UsuarioNaoEncontradoException
   * @return ProblemDetail no modelo RFC 7807 com detalhes do erro
   */
  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ProblemDetail handleNaoEncontrado(UsuarioNaoEncontradoException ex) {
    log.warn("Usuário não encontrado: {}", ex.getMessage());
    return construirProblem(HttpStatus.NOT_FOUND, "Usuário não encontrado", ex.getMessage(), null);
  }

  /**
   * Manipulador de exceção para e-mail já cadastrado.
   *
   * @param ex exceção EmailJaCadastradoException
   * @return ProblemDetail no modelo RFC 7807 com detalhes do erro
   */
  @ExceptionHandler(EmailJaCadastradoException.class)
  public ProblemDetail handleEmailDuplicado(EmailJaCadastradoException ex) {
    log.warn("E-mail duplicado: {}", ex.getMessage());
    return construirProblem(HttpStatus.CONFLICT, "E-mail já cadastrado", ex.getMessage(), null);
  }

  /**
   * Manipulador de exceção para erros de validação de dados.
   *
   * @param ex exceção MethodArgumentNotValidException
   * @return ProblemDetail no modelo RFC 7807 com detalhes do erro
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidacao(MethodArgumentNotValidException ex) {
    Map<String, String> campos = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      campos.putIfAbsent(error.getField(), error.getDefaultMessage());
    }
    log.warn("Erro de validação: {}", campos);
    ProblemDetail problem = construirProblem(HttpStatus.UNPROCESSABLE_ENTITY, "Erro de validação",
        "Dados inválidos", campos);
    problem.setProperty("campos", campos);
    return problem;
  }

  /**
   * Manipulador de exceção genérica para erros inesperados.
   *
   * @param ex exceção genérica
   * @return ProblemDetail no modelo RFC 7807 com detalhes do erro
   */

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenerico(Exception ex) {
    log.error("Erro interno não esperado", ex);
    return construirProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno",
        "Ocorreu um erro inesperado", null);
  }

  /**
   * Manipulador de exceção para recursos não encontrados.
   *
   * @param ex exceção NoResourceFoundException
   * @return ProblemDetail no modelo RFC 7807 com detalhes do erro
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ProblemDetail handleNoResourceFound(NoResourceFoundException ex) {
    return construirProblem(HttpStatus.NOT_FOUND, "Recurso não encontrado", ex.getMessage(), null);
  }

  private ProblemDetail construirProblem(HttpStatus status, String titulo, String detalhe,
      Map<String, String> campos) {
    ProblemDetail problem = ProblemDetail.forStatus(status);
    problem.setTitle(titulo);
    problem.setDetail(detalhe);
    problem.setType(URI.create("ms-usuario/erros"));
    if (campos != null) {
      problem.setProperty("campos", campos);
    }
    return problem;
  }
}
