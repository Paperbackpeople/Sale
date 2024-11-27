-- MySQL dump 10.13  Distrib 9.1.0, for Linux (aarch64)
--
-- Host: localhost    Database: sale
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `address_line` varchar(255) NOT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `is_primary` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`address_id`),
  KEY `fk_user_address` (`user_id`),
  CONSTRAINT `fk_user_address` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,1,'www','89353349','北京市','北京市','东城区',0,'2024-11-20 16:08:22','2024-11-20 16:08:22'),(2,1,'weq','11111111111','北京市','北京市','丰台区',0,'2024-11-20 16:09:26','2024-11-20 16:09:26'),(3,1,'stven','8848','辽宁省','沈阳市','和平区',0,'2024-11-20 16:27:41','2024-11-20 16:27:41'),(4,1,'威尼斯人','89353349','澳门特别行政区','澳门半岛','花地玛堂区',0,'2024-11-20 16:42:25','2024-11-20 17:14:09'),(5,1,'oooo','8848','山西省','太原市','小店区',0,'2024-11-20 17:09:46','2024-11-20 17:09:46'),(6,1,'qqq','6789','河北省','石家庄市','长安区',0,'2024-11-20 17:10:38','2024-11-20 17:14:34'),(7,1,'nanyanjing','909919','江苏省','南京市','玄武区',1,'2024-11-20 17:14:34','2024-11-20 17:14:34'),(8,4,'huacheng','81921928','北京市','北京市','东城区',1,'2024-11-23 08:36:23','2024-11-23 08:36:23');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Hamburg'),(2,'Pizza'),(3,'Chinese Food'),(4,'Recommend');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_records`
--

DROP TABLE IF EXISTS `login_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `login_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_records`
--

LOCK TABLES `login_records` WRITE;
/*!40000 ALTER TABLE `login_records` DISABLE KEYS */;
INSERT INTO `login_records` VALUES (1,1,'2024-11-14 17:24:57'),(2,1,'2024-11-17 16:52:30'),(3,1,'2024-11-18 14:17:14'),(4,1,'2024-11-18 16:30:39'),(5,1,'2024-11-19 12:28:17'),(6,1,'2024-11-19 14:17:31'),(7,1,'2024-11-19 16:39:54'),(8,1,'2024-11-20 13:19:45'),(9,1,'2024-11-20 14:22:31'),(10,1,'2024-11-20 15:26:29'),(11,1,'2024-11-20 16:02:57'),(12,1,'2024-11-21 05:57:28'),(13,1,'2024-11-21 06:30:42'),(14,1,'2024-11-21 06:59:08'),(15,1,'2024-11-22 17:01:16'),(16,1,'2024-11-23 04:28:46'),(17,1,'2024-11-23 04:30:10'),(18,1,'2024-11-23 04:46:22'),(19,1,'2024-11-23 06:16:41'),(20,1,'2024-11-23 06:19:36'),(21,1,'2024-11-23 06:23:33'),(22,1,'2024-11-23 06:30:26'),(23,1,'2024-11-23 06:46:27'),(24,1,'2024-11-23 06:58:04'),(25,1,'2024-11-23 07:05:46'),(26,1,'2024-11-23 07:36:46'),(27,1,'2024-11-23 07:39:09'),(28,1,'2024-11-23 07:40:03'),(29,1,'2024-11-23 08:17:25'),(30,1,'2024-11-23 08:21:05'),(31,2,'2024-11-23 08:23:04'),(32,3,'2024-11-23 08:25:26'),(33,4,'2024-11-23 08:32:43'),(34,4,'2024-11-23 09:19:23'),(35,4,'2024-11-23 09:25:00'),(36,5,'2024-11-24 08:25:31');
/*!40000 ALTER TABLE `login_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `orderItemId` bigint NOT NULL AUTO_INCREMENT,
  `orderId` bigint DEFAULT NULL,
  `productId` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`orderItemId`),
  KEY `orderId` (`orderId`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (8,515417902883672064,1,2,40.00),(9,515417902883672064,NULL,2,40.00),(10,515424037296541696,3,1,55.00),(11,515424401156608000,4,2,80.00),(12,515424401156608000,NULL,3,40.00),(13,515445529317085184,1,3,40.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` bigint NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `totalAmount` decimal(10,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `token` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (515417902883672064,'1241077523@qq.com',160.00,'pending','2024-11-23 06:46:44','2024-11-23 06:46:44','8cc652e5-4f55-4459-94d8-b997d9fed9d5','stven, 沈阳市, 和平区, 辽宁省'),(515424037296541696,'1241077523@qq.com',55.00,'pending','2024-11-23 07:11:06','2024-11-23 07:11:06','e703a68d-b7a8-4e0b-8828-ff1d97fc7a75','stven, 沈阳市, 和平区, 辽宁省'),(515424401156608000,'1241077523@qq.com',280.00,'pending','2024-11-23 07:12:33','2024-11-23 07:12:33','d4cd7d23-8d24-4898-b468-7ffe4d21a001','weq, 北京市, 丰台区, 北京市'),(515445529317085184,'2551281979@qq.com',120.00,'pending','2024-11-23 08:36:30','2024-11-23 08:36:30','c1aa585e-e4b1-4a07-8e24-adbe9d9a5db4','huacheng, 北京市, 东城区, 北京市');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_categories`
--

DROP TABLE IF EXISTS `product_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_categories` (
  `product_id` int NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`product_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `product_categories_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `product_categories_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_categories`
--

LOCK TABLES `product_categories` WRITE;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` VALUES (1,4),(2,4),(3,4),(4,4);
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_details` (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `full_description` text,
  PRIMARY KEY (`detail_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_details_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_details`
--

LOCK TABLES `product_details` WRITE;
/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` VALUES (1,1,'Hamburg is a juicy, grilled beef patty served on a fresh bun with lettuce, tomato, and sauces.'),(2,2,'Lemonade is a refreshing blend of lemon juice, sugar, and cold water, perfect for hot days.'),(3,3,'Pizza is a classic Italian dish with a crispy crust, topped with tomato sauce, melted cheese, and various toppings.'),(4,4,'Roastduck is a traditional delicacy featuring crispy skin and tender, flavorful meat, served with a side of plum sauce.');
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) NOT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,'Hamburg_01.JPG',1),(2,'Hamburg_02.JPG',1),(3,'Hamburg_03.JPG',1),(4,'Hamburg_04.JPG',1),(5,'Hamburg_05.JPG',1),(6,'lemonade_01.JPG',2),(7,'lemonade_02.JPG',2),(8,'lemonade_03.JPG',2),(9,'pizza_01.JPG',3),(10,'pizza_02.JPG',3),(11,'pizza_03.JPG',3),(12,'pizza_04.JPG',3),(13,'pizza_05.JPG',3),(14,'roastduck_01.JPG',4),(15,'roastduck_02.JPG',4),(16,'roastduck_03.JPG',4),(17,'roastduck_04.JPG',4),(18,'https://p0.pipi.cn/mediaplus/friday_image_fe/cdf05c5cb78e3205116cb409649007bc6e87d.jpg?imageMogr2/quality/80',5),(19,'https://p0.pipi.cn/basicdata/54ecde5106d92387a93139335c5c9286fb93f.jpg?imageMogr2/thumbnail/2500x2500%3E',6),(20,'https://p0.pipi.cn/mediaplus/friday_image_fe/cdf05c5c15d4a19a138d3e278b5bcdce77921.jpg?imageMogr2/quality/80',7),(21,'https://p0.pipi.cn/mediaplus/friday_image_fe/cdf05c5cea414d5be715d53346e355b72ec78.jpeg?imageMogr2/quality/80',8),(22,'https://p0.pipi.cn/mediaplus/friday_image_fe/cdf05c5c77e52993b6a2190af8330c8b65f9b.jpg?imageMogr2/quality/80',9);
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `short_description` text,
  `price` decimal(10,2) NOT NULL,
  `sale_volume` int DEFAULT '0',
  `rating` decimal(2,1) DEFAULT '0.0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Hamburg','Very popular',40.00,22,9.0),(2,'Lemonade','Refreshing and cool drink',10.00,50,8.5),(3,'Pizza','Cheesy and delicious',55.00,30,9.3),(4,'Roastduck','Crispy and flavorful dish',80.00,12,9.0),(5,'角斗士2','Movie: 角斗士2',40.00,0,0.0),(6,'鸳鸯楼·惊魂','Movie: 鸳鸯楼·惊魂',40.00,0,0.0),(7,'胜券在握','Movie: 胜券在握',40.00,0,0.0),(8,'风流一代','Movie: 风流一代',40.00,0,0.0),(9,'好东西','Movie: 好东西',40.00,0,0.0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'1241077523@qq.com','2024-11-14 17:24:57','Zhaoyu'),(2,'124107523@qq.com','2024-11-23 08:23:04','wzy'),(3,'1234@qq.com','2024-11-23 08:25:26','lean'),(4,'2551281979@qq.com','2024-11-23 08:32:43','Lean'),(5,'1@qq.com','2024-11-24 08:25:31','w');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-24 10:25:33
