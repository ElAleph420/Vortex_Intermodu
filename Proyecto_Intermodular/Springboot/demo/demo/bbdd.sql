-- Estructura de la base de datos Vortex corregida
CREATE DATABASE IF NOT EXISTS vortex;
USE vortex;

-- 1. Tabla de Planes (Ajustada a lo que busca tu código Java)
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan` (
  `id_plan` int NOT NULL AUTO_INCREMENT,
  `tipo_plan` varchar(20) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_plan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Tabla de Usuarios
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `dni` varchar(9) NOT NULL,
  `id_plan` int NOT NULL,
  `email` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `fecha_registro` date NOT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `email` (`email`),
  KEY `id_plan` (`id_plan`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_plan`) REFERENCES `plan` (`id_plan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Tabla de Servicios
DROP TABLE IF EXISTS `servicio`;
CREATE TABLE `servicio` (
  `id_servicio` int NOT NULL AUTO_INCREMENT,
  `nombre_servicio` varchar(100) NOT NULL,
  PRIMARY KEY (`id_servicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Tabla de Contraseñas
DROP TABLE IF EXISTS `contrasenya`;
CREATE TABLE `contrasenya` (
  `id_contrasenya` int NOT NULL AUTO_INCREMENT,
  `dni_usuario` varchar(9) NOT NULL,
  `valor_cifrado` varchar(255) NOT NULL,
  `fecha_creacion` date NOT NULL,
  `fecha_caducidad_gestor` date DEFAULT NULL,
  PRIMARY KEY (`id_contrasenya`),
  KEY `dni_usuario` (`dni_usuario`),
  CONSTRAINT `contrasenya_ibfk_1` FOREIGN KEY (`dni_usuario`) REFERENCES `usuario` (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Tabla Relacional Contraseña_Servicio
DROP TABLE IF EXISTS `contrasenya_servicio`;
CREATE TABLE `contrasenya_servicio` (
  `id_contrasenya` int NOT NULL,
  `id_servicio` int NOT NULL,
  `fecha_expiracion` date DEFAULT NULL,
  PRIMARY KEY (`id_contrasenya`,`id_servicio`),
  KEY `id_servicio` (`id_servicio`),
  CONSTRAINT `contrasenya_servicio_ibfk_1` FOREIGN KEY (`id_contrasenya`) REFERENCES `contrasenya` (`id_contrasenya`),
  CONSTRAINT `contrasenya_servicio_ibfk_2` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`id_servicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- INSERTS DE SEGURIDAD (Para que el registro no explote)
INSERT INTO `plan` (`tipo_plan`, `precio`, `descripcion`) VALUES 
('premium', 9.99, 'Acceso total a Vortex Keygen'),
('basico', 0.00, 'Acceso limitado');

INSERT INTO `servicio` (`nombre_servicio`) VALUES 
('Netflix'), ('Spotify'), ('Amazon Prime'), ('Steam');
