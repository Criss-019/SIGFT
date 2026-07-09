-- Script de inicialización de bases de datos SIGFT
-- Se ejecuta automáticamente cuando MySQL arranca por primera vez

-- Crear bases de datos para cada microservicio
CREATE DATABASE IF NOT EXISTS pasajeros_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS tramites_db  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS seguridad_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS reportes_db  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS integracion_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Dar permisos al usuario sigft_user en todas las bases
GRANT ALL PRIVILEGES ON pasajeros_db.*   TO 'sigft_user'@'%';
GRANT ALL PRIVILEGES ON tramites_db.*    TO 'sigft_user'@'%';
GRANT ALL PRIVILEGES ON seguridad_db.*   TO 'sigft_user'@'%';
GRANT ALL PRIVILEGES ON reportes_db.*    TO 'sigft_user'@'%';
GRANT ALL PRIVILEGES ON integracion_db.* TO 'sigft_user'@'%';

FLUSH PRIVILEGES;
