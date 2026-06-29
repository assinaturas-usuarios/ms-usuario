package com.empresa.usuario.application.usecase;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.domain.port.in.CadastrarUsuarioUseCase;
import com.empresa.usuario.domain.port.out.UsuarioRepositoryPort;
import com.empresa.usuario.infrastructure.exception.EmailJaCadastradoException;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação do caso de uso para cadastrar usuários.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CadastrarUsuarioUseCaseImpl implements CadastrarUsuarioUseCase {

  private final UsuarioRepositoryPort repository;
  private final UsuarioMapper mapper;

  @Override
  @Transactional
  @CachePut(value = "usuarios", key = "#result.id()")
  public UsuarioResponse cadastrar(CadastrarUsuarioRequest request) {
    log.info("Cadastrando usuario com e-mail: {}", request.email());
    validarEmailUnico(request.email());
    Usuario usuario = Usuario.novo(request.nome(), request.email());
    Usuario persistido = repository.salvar(usuario);
    log.info("Usuario cadastrado com sucesso: id={}", persistido.getId());
    return mapper.toResponse(persistido);
  }

  private void validarEmailUnico(String email) {
    if (repository.existePorEmail(email)) {
      log.warn("Tentativa de cadastro com e-mail ja existente: {}", email);
      throw new EmailJaCadastradoException(email);
    }
  }
}