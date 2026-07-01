package com.empresa.usuario.infrastructure.adapter.in.rest;

import com.empresa.usuario.application.dto.CadastrarUsuarioRequest;
import com.empresa.usuario.application.dto.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UsuarioResponse.class))),
      @ApiResponse(responseCode = "409", description = "E-mail já cadastrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProblemDetail.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "type": "ms-usuario/errors",
                        "title": "Conflito: E-mail Já Cadastrado",
                        "status": 409,
                        "detail": "O e-mail ana@email.com já está cadastrado",
                        "instance": "POST /v1/usuarios"
                      }
                      """
              ))),
      @ApiResponse(responseCode = "422", description = "Dados inválidos",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProblemDetail.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "type": "ms-usuario/errors",
                        "title": "Erro de Validação",
                        "status": 422,
                        "detail": "Os dados fornecidos contêm erros de validação",
                        "instance": "POST /v1/usuarios",
                        "campos": {
                          "email": "Email inválido",
                          "nome": "Nome é obrigatório"
                        }
                      }
                      """
              ))),
      @ApiResponse(responseCode = "503", description = "Banco de dados indisponível",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProblemDetail.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "type": "ms-usuario/errors",
                        "title": "Banco de Dados Indisponível",
                        "status": 503,
                        "detail": "O banco de dados está temporariamente indisponível. Tente novamente em alguns instantes.",
                        "instance": "POST /v1/usuarios"
                      }
                      """
              )))
  })
  UsuarioResponse cadastrar(@Valid @RequestBody CadastrarUsuarioRequest request);

  /**
   * Endpoint para buscar um usuário pelo seu ID.
   *
   * @param id identificador único do usuário
   * @return dados do usuário encontrado
   */
  @GetMapping("/{id}")
  @Operation(summary = "Buscar usuário por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Usuário encontrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = UsuarioResponse.class))),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProblemDetail.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "type": "ms-usuario/errors",
                        "title": "Usuário Não Encontrado",
                        "status": 404,
                        "detail": "Usuário com ID 550e8400-e29b-41d4-a716-446655440000 não foi encontrado",
                        "instance": "GET /v1/usuarios/550e8400-e29b-41d4-a716-446655440000"
                      }
                      """
              ))),
      @ApiResponse(responseCode = "503", description = "Banco de dados indisponível",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProblemDetail.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "type": "ms-usuario/errors",
                        "title": "Banco de Dados Indisponível",
                        "status": 503,
                        "detail": "O banco de dados está temporariamente indisponível. Tente novamente em alguns instantes.",
                        "instance": "GET /v1/usuarios/550e8400-e29b-41d4-a716-446655440000"
                      }
                      """
              )))
  })
  UsuarioResponse buscarPorId(
      @Parameter(description = "ID do usuário", required = true) @PathVariable UUID id);
}
