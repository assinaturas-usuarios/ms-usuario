package com.empresa.usuario.domain.port.in;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;

/**
 * Use case para cadastrar usuários.
 */
public interface CadastrarUsuarioUseCase {

  /**
   * Cadastra um novo usuário no sistema.
   *
   * @param request dados do usuário a ser cadastrado
   * @return dados do usuário cadastrado
   */
  UsuarioResponse cadastrar(CadastrarUsuarioRequest request);
}
