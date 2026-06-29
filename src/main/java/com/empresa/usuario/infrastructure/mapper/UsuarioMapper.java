package com.empresa.usuario.infrastructure.mapper;

import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.infrastructure.adapter.out.persistence.UsuarioEntity;
import org.mapstruct.Mapper;

/**
 * Mapper para conversão entre entidades de domínio, entidades de persistência e DTOs.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

  /**
   * Converte entidade de domínio para entidade de persistência.
   *
   * @param usuario entidade de domínio
   * @return entidade JPA
   */
  UsuarioEntity toEntity(Usuario usuario);

  /**
   * Converte entidade de persistência para entidade de domínio.
   *
   * @param entidade entidade JPA
   * @return entidade de domínio
   */
  Usuario toDomain(UsuarioEntity entidade);

  /**
   * Converte entidade de domínio para DTO de resposta.
   *
   * @param usuario entidade de domínio
   * @return DTO de resposta
   */
  UsuarioResponse toResponse(Usuario usuario);
}
