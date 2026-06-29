package com.empresa.usuario.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA representando um usuário.
 */
@Entity
@Table(name = "usuarios",
    uniqueConstraints = @UniqueConstraint(name = "uk_usuario_email", columnNames = "email"))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, length = 150)
  private String nome;

  @Column(nullable = false, unique = true, length = 200)
  private String email;

  @Column(name = "data_cadastro", nullable = false)
  private LocalDateTime dataCadastro;
}
