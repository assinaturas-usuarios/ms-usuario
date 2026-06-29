package com.empresa.usuario.infrastructure.exception;

import java.util.UUID;

/**
 * Exceção lançada quando um usuário não é encontrado.
 */
public class UsuarioNaoEncontradoException extends RuntimeException {

  /**
   * Construtor da exceção.
   *
   * @param id ID do usuário que não foi encontrado
   */
  public UsuarioNaoEncontradoException(UUID id) {
    super("Usuário não encontrado: " + id);
  }
}
