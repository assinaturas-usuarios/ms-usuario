# ms-usuario

Microsserviço responsável pelo cadastro e consulta de usuários da plataforma de streaming.

## Tecnologias

- Java 25 + Spring Boot 3.5.3
- Spring MVC + JPA + PostgreSQL 16
- Spring Cache + Redis (`@Cacheable`)
- Flyway para migrações
- MapStruct para mapeamento
- Swagger/OpenAPI 3 (springdoc 2.8.9)
- Micrometer Tracing + Zipkin
- JaCoCo (90% de cobertura)

## Arquitetura

Arquitetura hexagonal (ports & adapters):

- `domain/` — modelos e portas de negócio
- `application/` — casos de uso e DTOs
- `infrastructure/` — adapters REST, JPA, handlers

## Endpoints

| Método | Path                | Descrição              |
|--------|---------------------|------------------------|
| POST   | `/v1/usuarios`      | Cadastrar novo usuário |
| GET    | `/v1/usuarios/{id}` | Buscar usuário por ID  |

### Exemplo de requisição

```bash
curl -X POST http://localhost:8081/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome": "Ana Lima", "email": "ana@email.com"}'
```

### Exemplo de resposta

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "nome": "Ana Lima",
  "email": "ana@email.com",
  "dataCadastro": "2024-01-15T10:30:00"
}
```

## Variáveis de Ambiente

| Variável      | Descrição              | Padrão      |
|---------------|------------------------|-------------|
| `DB_URL`      | JDBC URL do PostgreSQL | obrigatório |
| `DB_USERNAME` | Usuário do banco       | obrigatório |
| `DB_PASSWORD` | Senha do banco         | obrigatório |
| `REDIS_HOST`  | Host do Redis          | localhost   |
| `REDIS_PORT`  | Porta do Redis         | 6379        |
| `ZIPKIN_URL`  | URL do Zipkin          | opcional    |

## Como executar

```bash
# Com toda a infraestrutura via Docker Compose (a partir da raiz)
docker compose up --build

# Apenas a infraestrutura, serviço rodando localmente
cd ..
docker compose up postgres redis -d
cd ms-usuario
./mvnw spring-boot:run
```

## Documentação da API

Com o serviço em execução:

- Swagger UI: http://localhost:8081/swagger-ui.html
- OpenAPI JSON: http://localhost:8081/v3/api-docs
- Actuator: http://localhost:8081/actuator/health

## Banco de dados

- **Database**: `usuario_db`
- **Porta**: 5432
- **Migração**: `V1__criar_tabela_usuarios.sql`

## Testes

```bash
./mvnw test      # Executar testes
./mvnw verify    # Testes + verificação de cobertura JaCoCo
```

Cobertura mínima configurada: **90% de linhas**.

---

### Arquitetura Hexagonal

O serviço usa a mesma separação de camadas dos demais: `domain/port/in` para contratos de entrada, `domain/port/out` para saída, `application/usecase` para regras e `infrastructure` para adapters JPA e REST. A camada de domínio não depende de Spring Data ou JPA.

### Spring MVC (Síncrono) vs WebFlux

Ao contrário do ms-assinatura, este serviço usa Spring MVC clássico com JPA. O ms-usuario funciona como um serviço simples de cadastro sem requisitos de alta concorrência reativa. O uso de MVC simplifica o modelo de programação sem perda de throughput para o volume esperado.

### Cache com @Cacheable (Redis)

O método de busca por ID usa `@Cacheable` do Spring Cache, com backend Redis. Isso evita consultas repetidas ao banco para usuários frequentemente acessados pelo ms-assinatura via Circuit Breaker. A chave de cache inclui o UUID do usuário.

### MapStruct

O mapeamento entre a entidade JPA e os DTOs de resposta é gerado em tempo de compilação pelo MapStruct, eliminando reflexão em tempo de execução e garantindo erros de mapeamento como falhas de compilação.

### Flyway

As migrações do banco são versionadas com Flyway. Novos scripts SQL em `resources/db/migration` são executados automaticamente na inicialização, garantindo que o esquema esteja sempre sincronizado com o código.

### ProblemDetail (RFC 7807)

Erros retornam respostas estruturadas no padrão RFC 7807 via handler global, com `type`, `title`, `status` e `detail` consistentes com os demais microsserviços.


