package com.empresa.usuario.domain.port.in;

import com.empresa.usuario.application.dto.UsuarioResponse;
import java.util.UUID;

/**
 * Use case para buscar usuários.
 */
public interface BuscarUsuarioUseCase {

  /**
   * Busca um usuário pelo seu identificador único.
   *
   * @param id identificador do usuário
   * @return dados do usuário encontrado
   */
  UsuarioResponse buscarPorId(UUID id);
}
