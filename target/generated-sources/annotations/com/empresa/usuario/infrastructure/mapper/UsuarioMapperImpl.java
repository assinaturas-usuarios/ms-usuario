package com.empresa.usuario.infrastructure.mapper;

import com.empresa.usuario.application.dto.UsuarioResponse;
import com.empresa.usuario.domain.model.Usuario;
import com.empresa.usuario.infrastructure.adapter.out.persistence.UsuarioEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-29T19:43:00-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioEntity toEntity(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioEntity.UsuarioEntityBuilder usuarioEntity = UsuarioEntity.builder();

        usuarioEntity.id( usuario.getId() );
        usuarioEntity.nome( usuario.getNome() );
        usuarioEntity.email( usuario.getEmail() );
        usuarioEntity.dataCadastro( usuario.getDataCadastro() );

        return usuarioEntity.build();
    }

    @Override
    public Usuario toDomain(UsuarioEntity entidade) {
        if ( entidade == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String email = null;
        LocalDateTime dataCadastro = null;

        id = entidade.getId();
        nome = entidade.getNome();
        email = entidade.getEmail();
        dataCadastro = entidade.getDataCadastro();

        Usuario usuario = new Usuario( id, nome, email, dataCadastro );

        return usuario;
    }

    @Override
    public UsuarioResponse toResponse(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UUID id = null;
        String nome = null;
        String email = null;
        LocalDateTime dataCadastro = null;

        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
        dataCadastro = usuario.getDataCadastro();

        UsuarioResponse usuarioResponse = new UsuarioResponse( id, nome, email, dataCadastro );

        return usuarioResponse;
    }
}
