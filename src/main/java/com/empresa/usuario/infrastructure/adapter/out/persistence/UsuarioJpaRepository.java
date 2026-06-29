package com.empresa.usuario.infrastructure.adapter.out.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório JPA para operações de persistência de usuários.
 */
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, UUID> {

  /**
   * Verifica se um usuário com o e-mail especificado já existe.
   *
   * @param email e-mail do usuário
   * @return true se o usuário existir, false caso contrário
   */
  boolean existsByEmail(String email);
}
