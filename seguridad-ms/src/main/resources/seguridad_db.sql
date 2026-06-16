-- seguridad_db schema
CREATE DATABASE IF NOT EXISTS seguridad_db;
USE seguridad_db;

CREATE TABLE IF NOT EXISTS usuarios_sistema (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    username         VARCHAR(60)  NOT NULL UNIQUE,
    password_hash    VARCHAR(255) NOT NULL,
    nombre_completo  VARCHAR(150) NOT NULL,
    email            VARCHAR(120) NOT NULL UNIQUE,
    rol              VARCHAR(30)  NOT NULL,
    activo           BOOLEAN      NOT NULL DEFAULT TRUE,
    fecha_creacion   DATETIME     NOT NULL,
    fecha_ultimo_acceso DATETIME
);

INSERT IGNORE INTO usuarios_sistema (username, password_hash, nombre_completo, email, rol, activo, fecha_creacion)
VALUES
  ('admin',       '$2a$12$dummyHashAdmin',       'Administrador SIGFT',     'admin@sigft.cl',       'ADMINISTRADOR',       TRUE, NOW()),
  ('func_aduana', '$2a$12$dummyHashAduana',       'Juan Pérez Aduana',       'jaduana@sigft.cl',     'FUNCIONARIO_ADUANA',  TRUE, NOW()),
  ('func_sag',    '$2a$12$dummyHashSag',          'María López SAG',         'msag@sigft.cl',        'FUNCIONARIO_SAG',     TRUE, NOW()),
  ('func_pdi',    '$2a$12$dummyHashPdi',          'Carlos Rojas PDI',        'cpdi@sigft.cl',        'FUNCIONARIO_PDI',     TRUE, NOW()),
  ('gerente',     '$2a$12$dummyHashGerente',      'Ana Muñoz Gerente',       'gerente@sigft.cl',     'CLIENTE_GERENTE',     TRUE, NOW());
