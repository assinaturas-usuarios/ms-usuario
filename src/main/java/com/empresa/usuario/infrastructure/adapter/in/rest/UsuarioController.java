package com.empresa.usuario.infrastructure.adapter.in.rest;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.port.in.BuscarUsuarioUseCase;
import com.empresa.usuario.domain.port.in.CadastrarUsuarioUseCase;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para operações relacionadas a usuários.
 */
@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController implements UsuarioSwagger {

  private final CadastrarUsuarioUseCase cadastrarUseCase;
  private final BuscarUsuarioUseCase buscarUseCase;

  @Override
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UsuarioResponse cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request) {
    log.info("Recebendo POST request de criação de usuário, e-mail: {}", request.email());
    return cadastrarUseCase.cadastrar(request);
  }

  @Override
  @GetMapping("/{id}")
  public UsuarioResponse buscarPorId(@PathVariable UUID id) {
    log.info("Recebendo GET request de busca de usuário por id: {}", id);
    return buscarUseCase.buscarPorId(id);
  }
}
