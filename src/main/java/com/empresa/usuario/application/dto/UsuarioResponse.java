package com.empresa.usuario.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de resposta para informações do usuário.
 *
 * @param id           Identificador único do usuário
 * @param nome         Nome do usuário
 * @param email        Email do usuário
 * @param dataCadastro Data e hora de cadastro do usuário
 */
public record UsuarioResponse(
    UUID id,
    String nome,
    String email,
    LocalDateTime dataCadastro
) {

}
