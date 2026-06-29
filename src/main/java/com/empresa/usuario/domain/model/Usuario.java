package com.empresa.usuario.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um usuário no sistema, model sem referência a frameworks específicos.
 */
public class Usuario {

  private UUID id;
  private String nome;
  private String email;
  private LocalDateTime dataCadastro;

  /**
   * Construtor privado para criar uma instância de Usuario.
   *
   * @param id           identificador único do usuário
   * @param nome         nome do usuário
   * @param email        e-mail do usuário
   * @param dataCadastro data e hora de cadastro do usuário
   */
  public Usuario(UUID id, String nome, String email, LocalDateTime dataCadastro) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.dataCadastro = dataCadastro;
  }

  /**
   * Cria um novo usuário com um ID gerado e a data de cadastro atual.
   *
   * @param nome  nome do usuário
   * @param email e-mail do usuário
   * @return instância de Usuario
   */
  public static Usuario novo(String nome, String email) {
    return new Usuario(null, nome, email, LocalDateTime.now());
  }

  public UUID getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getDataCadastro() {
    return dataCadastro;
  }
}
