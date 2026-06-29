package com.empresa.usuario.application.usecase;

import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.port.in.BuscarUsuarioUseCase;
import com.empresa.usuario.domain.port.out.UsuarioRepositoryPort;
import com.empresa.usuario.infrastructure.exception.UsuarioNaoEncontradoException;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação do caso de uso para buscar usuários.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BuscarUsuarioUseCaseImpl implements BuscarUsuarioUseCase {

  private final UsuarioRepositoryPort repository;
  private final UsuarioMapper mapper;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "usuarios", key = "#id")
  public UsuarioResponse buscarPorId(UUID id) {
    log.info("Buscando usuário: id={}", id);
    return repository.buscarPorId(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
  }
}