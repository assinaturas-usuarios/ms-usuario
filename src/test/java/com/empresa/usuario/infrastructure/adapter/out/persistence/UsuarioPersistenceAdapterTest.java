package com.empresa.usuario.infrastructure.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.infrastructure.mapper.UsuarioMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioPersistenceAdapter")
class UsuarioPersistenceAdapterTest {

  @Mock
  private UsuarioJpaRepository jpaRepository;

  @Mock
  private UsuarioMapper mapper;

  @InjectMocks
  private UsuarioPersistenceAdapter adapter;

  @Test
  @DisplayName("Deve salvar usuario e retornar dominio")
  void deveSalvarUsuario() {
    Usuario usuario = Usuario.novo("Ana", "ana@test.com");
    UsuarioEntity entidade = new UsuarioEntity();
    UsuarioEntity salvo = new UsuarioEntity();
    when(mapper.toEntity(any())).thenReturn(entidade);
    when(jpaRepository.save(entidade)).thenReturn(salvo);
    when(mapper.toDomain(salvo)).thenReturn(usuario);

    Usuario result = adapter.salvar(usuario);

    assertThat(result).isEqualTo(usuario);
  }

  @Test
  @DisplayName("Deve retornar usuario ao buscar por id existente")
  void deveBuscarPorIdExistente() {
    UUID id = UUID.randomUUID();
    UsuarioEntity entidade = new UsuarioEntity();
    Usuario usuario = Usuario.novo("Bob", "bob@test.com");
    when(jpaRepository.findById(id)).thenReturn(Optional.of(entidade));
    when(mapper.toDomain(entidade)).thenReturn(usuario);

    Optional<Usuario> result = adapter.buscarPorId(id);

    assertThat(result).contains(usuario);
  }

  @Test
  @DisplayName("Deve retornar vazio ao buscar id inexistente")
  void deveRetornarVazioParaIdInexistente() {
    when(jpaRepository.findById(any())).thenReturn(Optional.empty());

    Optional<Usuario> result = adapter.buscarPorId(UUID.randomUUID());

    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("Deve verificar existencia por email")
  void deveVerificarExistenciaPorEmail() {
    when(jpaRepository.existsByEmail("ativo@test.com")).thenReturn(true);
    when(jpaRepository.existsByEmail("novo@test.com")).thenReturn(false);

    assertThat(adapter.existePorEmail("ativo@test.com")).isTrue();
    assertThat(adapter.existePorEmail("novo@test.com")).isFalse();
  }
}