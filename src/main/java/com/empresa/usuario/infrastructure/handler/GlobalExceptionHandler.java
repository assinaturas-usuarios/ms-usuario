package com.empresa.usuario.infrastructure.handler;

import com.empresa.usuario.infrastructure.exception.EmailJaCadastradoException;
import com.empresa.usuario.infrastructure.exception.UsuarioNaoEncontradoException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Manipulador global de exceções para a aplicação.
 * Retorna ProblemDetail no formato RFC 7807 para todos os erros.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Manipulador de exceção para usuário não encontrado (404).
   * 
   * @param ex UsuarioNaoEncontradoException lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro
   */
  @ExceptionHandler(UsuarioNaoEncontradoException.class)
  public ProblemDetail handleNaoEncontrado(UsuarioNaoEncontradoException ex) {
    log.warn("Usuário não encontrado: {}", ex.getMessage());
    return construirProblem(HttpStatus.NOT_FOUND, "Usuário Não Encontrado", ex.getMessage(),
        null);
  }

  /**
   * Manipulador de exceção para e-mail já cadastrado (409).
   * 
   * @param ex EmailJaCadastradoException lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro
   */
  @ExceptionHandler(EmailJaCadastradoException.class)
  public ProblemDetail handleEmailDuplicado(EmailJaCadastradoException ex) {
    log.warn("E-mail duplicado: {}", ex.getMessage());
    return construirProblem(HttpStatus.CONFLICT, "Conflito: E-mail Já Cadastrado",
        ex.getMessage(), null);
  }

  /**
   * Manipulador de exceção para erros de validação de dados (422).
   * 
   * @param ex MethodArgumentNotValidException lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro e os campos com erros de validação
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidacao(MethodArgumentNotValidException ex) {
    Map<String, String> campos = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      campos.putIfAbsent(error.getField(), error.getDefaultMessage());
    }
    log.warn("Erro de validação: {}", campos);
    ProblemDetail problem = construirProblem(HttpStatus.UNPROCESSABLE_ENTITY,
        "Erro de Validação", "Os dados fornecidos contêm erros de validação", campos);
    return problem;
  }

  /**
   * Manipulador de exceção para recursos não encontrados (404).
   * 
   * @param ex NoResourceFoundException lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ProblemDetail handleNoResourceFound(NoResourceFoundException ex) {
    log.warn("Recurso não encontrado: {}", ex.getMessage());
    return construirProblem(HttpStatus.NOT_FOUND, "Recurso Não Encontrado", ex.getMessage(),
        null);
  }

  /**
   * Manipulador para DataAccessException - erros de acesso ao banco de dados (503).
   * 
   * @param ex DataAccessException lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro
   */
  @ExceptionHandler(DataAccessException.class)
  public ProblemDetail handleDataAccessException(DataAccessException ex) {
    log.error("Erro de acesso ao banco de dados", ex);
    return construirProblem(HttpStatus.SERVICE_UNAVAILABLE, "Banco de Dados Indisponível",
        "O banco de dados está temporariamente indisponível. Tente novamente em alguns instantes.",
        null);
  }

  /**
   * Manipulador de exceção genérica para erros inesperados (500).
   * 
   * @param ex Exception lançada
   * @return um ProblemDetail, no formato RFC 7807, com informações sobre o erro
   */
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenerico(Exception ex) {
    log.error("Erro interno não esperado", ex);
    return construirProblem(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Interno do Servidor",
        "Ocorreu um erro inesperado. Consulte os logs para mais detalhes.", null);
  }

  private ProblemDetail construirProblem(HttpStatus status, String titulo, String detalhe,
      Map<String, String> campos) {
    ProblemDetail problem = ProblemDetail.forStatus(status);
    problem.setTitle(titulo);
    problem.setDetail(detalhe);
    problem.setType(URI.create("ms-usuario/errors"));
    if (campos != null && !campos.isEmpty()) {
      problem.setProperty("campos", campos);
    }
    return problem;
  }
}
