CREATE DATABASE IF NOT EXISTS pasajeros_db;
USE pasajeros_db;

CREATE TABLE pasajeros (
    rut VARCHAR(12) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    edad INT NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL
);

CREATE TABLE vehiculos (
    patente VARCHAR(10) PRIMARY KEY,
    tipo_vehiculo VARCHAR(50) NOT NULL,
    es_diplomatico BOOLEAN NOT NULL,
    plazo_maximo_dias INT NOT NULL,
    rut_pasajero VARCHAR(12) NOT NULL,
    FOREIGN KEY (rut_pasajero) REFERENCES pasajeros(rut)
);