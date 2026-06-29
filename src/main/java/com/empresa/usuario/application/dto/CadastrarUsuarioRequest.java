package com.empresa.usuario.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de cadastro de usuário.
 *
 * @param nome  Nome do usuário (obrigatório, máximo de 150 caracteres)
 * @param email E-mail do usuário (obrigatório, válido, máximo de 200 caracteres)
 */
public record CadastrarUsuarioRequest(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    String nome,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 200, message = "E-mail deve ter no máximo 200 caracteres")
    String email
) {

}
