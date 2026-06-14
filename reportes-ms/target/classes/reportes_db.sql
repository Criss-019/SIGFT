-- reportes_db schema
CREATE DATABASE IF NOT EXISTS reportes_db;
USE reportes_db;

CREATE TABLE IF NOT EXISTS reportes (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_reporte     VARCHAR(30)  NOT NULL,
    formato          VARCHAR(10)  NOT NULL,
    fecha_desde      DATE         NOT NULL,
    fecha_hasta      DATE         NOT NULL,
    total_registros  INT,
    nombre_archivo   VARCHAR(200),
    generado_por     VARCHAR(60)  NOT NULL,
    fecha_generacion DATETIME     NOT NULL,
    observaciones    VARCHAR(500)
);
