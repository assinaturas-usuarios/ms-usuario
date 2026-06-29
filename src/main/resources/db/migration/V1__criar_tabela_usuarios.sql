CREATE TABLE usuarios
(
    id            UUID         NOT NULL DEFAULT gen_random_uuid(),
    nome          VARCHAR(150) NOT NULL,
    email         VARCHAR(200) NOT NULL,
    data_cadastro TIMESTAMP    NOT NULL DEFAULT now(),
    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uk_usuario_email UNIQUE (email)
);

CREATE INDEX idx_usuario_email ON usuarios (email);
