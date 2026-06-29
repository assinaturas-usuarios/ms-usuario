package com.empresa.usuario.infrastructure.config;

import com.empresa.usuario.application.dto.UsuarioResponse;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * Configuração do cache Redis.
 */
@Configuration
@EnableCaching
public class RedisConfig {

  /**
   * Mixin para adicionar type info só na serialização Redis, não afeta a resposta HTTP.
   */
  @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
  abstract static class CacheableTypeMixin {}

  /**
   * Bean de configuração do cache Redis, definindo o tempo de vida do cache e a serialização dos
   * valores.
   */
  @Bean
  public RedisCacheConfiguration redisCacheConfiguration(ObjectMapper objectMapper) {
    ObjectMapper redisMapper = objectMapper.copy()
        .addMixIn(UsuarioResponse.class, CacheableTypeMixin.class);

    Jackson2JsonRedisSerializer<UsuarioResponse> serializer =
        new Jackson2JsonRedisSerializer<>(redisMapper, UsuarioResponse.class);
    
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(30))
        .disableCachingNullValues()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(serializer));
  }
}