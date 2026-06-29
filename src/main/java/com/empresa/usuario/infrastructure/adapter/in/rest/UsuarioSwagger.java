package com.empresa.usuario.infrastructure.adapter.in.rest;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Interface Swagger para documentação das APIs de usuários.
 */
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários")
public interface UsuarioSwagger {

  /**
   * Endpoint para cadastrar um novo usuário.
   *
   * @param request dados do usuário a ser cadastrado
   * @return dados do usuário cadastrado
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Cadastrar novo usuário")
  @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
  @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
  @ApiResponse(responseCode = "422", description = "Dados inválidos")
  UsuarioResponse cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request);

  /**
   * Endpoint para buscar um usuário pelo seu ID.
   *
   * @param id identificador único do usuário
   * @return dados do usuário encontrado
   */
  @GetMapping("/{id}")
  @Operation(summary = "Buscar usuário por ID")
  @ApiResponse(responseCode = "200", description = "Usuário encontrado")
  @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  UsuarioResponse buscarPorId(
      @Parameter(description = "ID do usuário", required = true) @PathVariable UUID id);
}
