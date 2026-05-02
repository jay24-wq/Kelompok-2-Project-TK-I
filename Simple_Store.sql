CREATE DATABASE  IF NOT EXISTS `simple_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `simple_store`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: simple_store
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `tabel_kategori`
--

DROP TABLE IF EXISTS `tabel_kategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabel_kategori` (
  `id_kategori` int NOT NULL AUTO_INCREMENT,
  `id_toko_fk` int NOT NULL,
  `nama_kategori` varchar(50) NOT NULL,
  PRIMARY KEY (`id_kategori`),
  UNIQUE KEY `uk_kategori_toko` (`id_toko_fk`,`nama_kategori`),
  CONSTRAINT `tabel_kategori_ibfk_1` FOREIGN KEY (`id_toko_fk`) REFERENCES `tabel_toko` (`id_toko`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabel_kategori`
--

LOCK TABLES `tabel_kategori` WRITE;
/*!40000 ALTER TABLE `tabel_kategori` DISABLE KEYS */;
INSERT INTO `tabel_kategori` VALUES (4,1,'Kopi'),(1,1,'Makanan'),(5,1,'Promo'),(2,2,'Makanan'),(9,4,'Elektronik'),(11,4,'Pakaian'),(10,4,'Promo'),(12,4,'Tas');
/*!40000 ALTER TABLE `tabel_kategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabel_produk`
--

DROP TABLE IF EXISTS `tabel_produk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabel_produk` (
  `id_produk` int NOT NULL AUTO_INCREMENT,
  `id_toko_fk` int NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` double NOT NULL,
  PRIMARY KEY (`id_produk`),
  KEY `id_toko_fk` (`id_toko_fk`),
  CONSTRAINT `tabel_produk_ibfk_1` FOREIGN KEY (`id_toko_fk`) REFERENCES `tabel_toko` (`id_toko`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabel_produk`
--

LOCK TABLES `tabel_produk` WRITE;
/*!40000 ALTER TABLE `tabel_produk` DISABLE KEYS */;
INSERT INTO `tabel_produk` VALUES (1,1,'beng beng',10000),(4,1,'Kopi Botol',15000),(5,2,'geprek',10000),(6,4,'Televisi',5000000);
/*!40000 ALTER TABLE `tabel_produk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabel_produk_kategori`
--

DROP TABLE IF EXISTS `tabel_produk_kategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabel_produk_kategori` (
  `id_produk_fk` int NOT NULL,
  `id_kategori_fk` int NOT NULL,
  PRIMARY KEY (`id_produk_fk`,`id_kategori_fk`),
  KEY `id_kategori_fk` (`id_kategori_fk`),
  CONSTRAINT `tabel_produk_kategori_ibfk_1` FOREIGN KEY (`id_produk_fk`) REFERENCES `tabel_produk` (`id_produk`) ON DELETE CASCADE,
  CONSTRAINT `tabel_produk_kategori_ibfk_2` FOREIGN KEY (`id_kategori_fk`) REFERENCES `tabel_kategori` (`id_kategori`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabel_produk_kategori`
--

LOCK TABLES `tabel_produk_kategori` WRITE;
/*!40000 ALTER TABLE `tabel_produk_kategori` DISABLE KEYS */;
INSERT INTO `tabel_produk_kategori` VALUES (1,1),(5,2),(4,4),(1,5),(4,5),(6,9),(6,10);
/*!40000 ALTER TABLE `tabel_produk_kategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabel_toko`
--

DROP TABLE IF EXISTS `tabel_toko`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabel_toko` (
  `id_toko` int NOT NULL AUTO_INCREMENT,
  `nama_toko` varchar(100) NOT NULL,
  `alamat_toko` varchar(255) DEFAULT NULL,
  `pemilik_toko` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_toko`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabel_toko`
--

LOCK TABLES `tabel_toko` WRITE;
/*!40000 ALTER TABLE `tabel_toko` DISABLE KEYS */;
INSERT INTO `tabel_toko` VALUES (1,'Toko Swalayan','Jl. Well no 67','Pria Sigma'),(2,'Toko Makanan Siap Saji','JL speed no 69','pria kecepatan'),(4,'HyperMart','Jl.Bypass Ngurah Rai','Tuan Hyper');
/*!40000 ALTER TABLE `tabel_toko` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-06 21:00:58
