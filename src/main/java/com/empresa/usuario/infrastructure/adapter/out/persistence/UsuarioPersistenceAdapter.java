package com.empresa.usuario.infrastructure.adapter.out.persistence;

import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.domain.port.out.UsuarioRepositoryPort;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adaptador de persistência para operações relacionadas a usuários.
 */
@Component
@RequiredArgsConstructor
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

  private final UsuarioJpaRepository jpaRepository;
  private final UsuarioMapper mapper;

  @Override
  public Usuario salvar(Usuario usuario) {
    UsuarioEntity entidade = mapper.toEntity(usuario);
    UsuarioEntity salvo = jpaRepository.save(entidade);
    return mapper.toDomain(salvo);
  }

  @Override
  public Optional<Usuario> buscarPorId(UUID id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public boolean existePorEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }
}
