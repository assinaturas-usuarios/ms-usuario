package com.empresa.usuario.domain.port.out;

import com.empresa.usuario.domain.model.Usuario;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de saída para operações de persistência de usuários.
 */
public interface UsuarioRepositoryPort {

  /**
   * Persiste um novo usuário.
   *
   * @param usuario entidade de domínio a ser salva
   * @return usuário persistido
   */
  Usuario salvar(Usuario usuario);

  /**
   * Busca usuário pelo identificador.
   *
   * @param id identificador do usuário
   * @return Optional com o usuário, se encontrado
   */
  Optional<Usuario> buscarPorId(UUID id);

  /**
   * Verifica se já existe um usuário com o e-mail informado.
   *
   * @param email e-mail a verificar
   * @return true se já existir
   */
  boolean existePorEmail(String email);
}
