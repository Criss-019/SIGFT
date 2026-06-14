-- integracion_db schema
CREATE DATABASE IF NOT EXISTS integracion_db;
USE integracion_db;

CREATE TABLE IF NOT EXISTS registros_integracion (
    id                           BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_tramite_ref               VARCHAR(50)  NOT NULL,
    rut_pasajero                 VARCHAR(12)  NOT NULL,
    tipo_operacion               VARCHAR(30)  NOT NULL,
    estado                       VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE',
    fecha_envio                  DATETIME,
    fecha_respuesta              DATETIME,
    codigo_respuesta_argentina   VARCHAR(20),
    mensaje_respuesta            VARCHAR(500),
    datos_enviados               TEXT,
    intentos_envio               INT DEFAULT 0,
    INDEX idx_rut_pasajero (rut_pasajero),
    INDEX idx_estado (estado),
    INDEX idx_tramite_ref (id_tramite_ref)
);
