-- Crear la base de datos si no existe y usarla
CREATE DATABASE IF NOT EXISTS tramites_db;
USE tramites_db;

-- -----------------------------------------------------
-- 1. Tabla Principal: Trámite Fronterizo
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tramites_fronterizos (
    id_tramite VARCHAR(50) PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    estado_tramite VARCHAR(20) NOT NULL,
    aduana_origen VARCHAR(100) NOT NULL,
    aduana_destino VARCHAR(100) NOT NULL
);

-- -----------------------------------------------------
-- 1.1 Tabla Auxiliar (@ElementCollection): Pasajeros del Trámite
-- Guarda los RUTs de los pasajeros asociados al trámite
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tramite_pasajeros (
    id_tramite VARCHAR(50) NOT NULL,
    rut_pasajero VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_tramite) REFERENCES tramites_fronterizos(id_tramite) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- 1.2 Tabla Auxiliar (@ElementCollection): Vehículos del Trámite
-- Guarda las patentes de los vehículos asociados al trámite
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tramite_vehiculos (
    id_tramite VARCHAR(50) NOT NULL,
    patente_vehiculo VARCHAR(15) NOT NULL,
    FOREIGN KEY (id_tramite) REFERENCES tramites_fronterizos(id_tramite) ON DELETE CASCADE
);

-- -----------------------------------------------------
-- 2. Tabla: Declaración Jurada SAG
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS declaraciones_sag (
    id_declaracion VARCHAR(50) PRIMARY KEY,
    fecha_registro DATETIME NOT NULL,
    trae_animales BOOLEAN NOT NULL DEFAULT FALSE,
    trae_vegetales BOOLEAN NOT NULL DEFAULT FALSE,
    posee_mascotas BOOLEAN NOT NULL DEFAULT FALSE,
    rut_pasajero VARCHAR(15) NOT NULL
);

-- -----------------------------------------------------
-- 3. Tabla: Autorización Notarial
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS autorizaciones_notariales (
    id_autorizacion VARCHAR(50) PRIMARY KEY,
    fecha_emision DATETIME NOT NULL,
    notaria_origen VARCHAR(100) NOT NULL,
    adjunto_pdf VARCHAR(255),
    rut_pasajero VARCHAR(15) NOT NULL
);