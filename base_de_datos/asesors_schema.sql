-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: seguridad_rrss
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(100) NOT NULL,
  `correo_contacto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Panadería Grupo Panificar','contacto@panificar.com'),(2,'Tienda Online TecnoTech','info@tecnotech.com'),(3,'Estudio Contable SmartCount','estudio@smartcount.com.ar'),(4,'Distribuidora EasyBuy','entregas@easybuy.com'),(6,'Exchange City','cambio@divisas.com.ar'),(7,'Supermercado Tienda Online','supermarket@tiendaonline.com.ar');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentas_rrss`
--

DROP TABLE IF EXISTS `cuentas_rrss`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentas_rrss` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `plataforma` enum('Facebook','Instagram','Twitter','LinkedIn','TikTok','Otro') NOT NULL,
  `nombre_cuenta` varchar(100) NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `cuentas_rrss_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentas_rrss`
--

LOCK TABLES `cuentas_rrss` WRITE;
/*!40000 ALTER TABLE `cuentas_rrss` DISABLE KEYS */;
INSERT INTO `cuentas_rrss` VALUES (1,1,'Instagram','@grupo.panificar.pan',1),(2,1,'Facebook','Panadería Grupo Panificar',1),(3,2,'Instagram','@tecnotech_store',1),(5,2,'Twitter','@tecnotech_tw',1),(6,3,'LinkedIn','Estudio SmartCount',1),(8,1,'Instagram','@grupo.panificar.pan',1),(9,1,'Facebook','Panadería Grupo Panificar',1),(10,2,'Instagram','@tecnotech_store',1),(11,2,'LinkedIn','TecnoTech Soluciones Online',1),(12,2,'Twitter','@tecnotech_tw',1),(13,3,'LinkedIn','Estudio SmartCount',1),(15,4,'Instagram','@distribuidora.easybuy',1),(16,1,'Instagram','grupo.panificar-empresas',1);
/*!40000 ALTER TABLE `cuentas_rrss` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diagnosticos`
--

DROP TABLE IF EXISTS `diagnosticos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diagnosticos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cuenta` int NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `cambio_password` tinyint(1) DEFAULT NULL,
  `tiene_2fa` tinyint(1) DEFAULT NULL,
  `actividad_sospechosa` tinyint(1) DEFAULT NULL,
  `resultado` enum('segura','comprometida','indeterminado') NOT NULL,
  `observaciones` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_diagnostico_por_cuenta` (`id_cuenta`),
  CONSTRAINT `diagnosticos_ibfk_1` FOREIGN KEY (`id_cuenta`) REFERENCES `cuentas_rrss` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diagnosticos`
--

LOCK TABLES `diagnosticos` WRITE;
/*!40000 ALTER TABLE `diagnosticos` DISABLE KEYS */;
INSERT INTO `diagnosticos` VALUES (6,2,'2025-06-07 14:50:27',0,0,0,'indeterminado','en revision'),(7,1,'2025-06-07 18:48:43',0,0,0,'comprometida','la password esta siendo usada en otros servicios'),(24,11,'2025-06-08 10:25:22',0,0,0,'indeterminado','Se recomienda rotar la password preventivamente, dado que se encontraba tambien en uso en otra plataforma y dicha platoforma sufrio una brecha de ciberseguridad.'),(26,3,'2025-06-08 11:12:41',0,0,0,'indeterminado','Verificar compromiso en brechas con HIBP');
/*!40000 ALTER TABLE `diagnosticos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `rol` enum('administrador','operador') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin01','tempadm987','administrador'),(3,'operador01','tempop123','operador');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08 14:00:14
