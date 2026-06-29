package com.empresa.usuario.infrastructure.exception;

/**
 * Exceção lançada quando um e-mail já está cadastrado no sistema.
 */
public class EmailJaCadastradoException extends RuntimeException {

  /**
   * Construtor da exceção.
   *
   * @param email e-mail que já está cadastrado
   */
  public EmailJaCadastradoException(String email) {
    super("E-mail já cadastrado: " + email);
  }
}
