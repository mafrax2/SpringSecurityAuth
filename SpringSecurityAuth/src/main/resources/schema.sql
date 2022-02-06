drop SCHEMA if exists db1;
create database db1;
use db1;

DROP TABLE IF EXISTS connection;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;

CREATE TABLE `db1`.`account` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `db1`.`connection` (
  `person_id` int NOT NULL,
  `friend_id` int NOT NULL,
  KEY `FKhxpl48p7ltqgnuorawcgovg8r` (`friend_id`),
  KEY `FKjtuiu46avpb8inp02gewy2mv7` (`person_id`),
  CONSTRAINT `FKhxpl48p7ltqgnuorawcgovg8r` FOREIGN KEY (`friend_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKjtuiu46avpb8inp02gewy2mv7` FOREIGN KEY (`person_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `db1`.`transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `emission_date` datetime(6) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `sender_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlb9w2lainsxqirtlwg231h1ir` (`receiver_id`),
  KEY `FKtadyjb9m2aggujattt7k2b0gy` (`sender_id`),
  CONSTRAINT `FKlb9w2lainsxqirtlwg231h1ir` FOREIGN KEY (`receiver_id`) REFERENCES `account` (`account_id`),
  CONSTRAINT `FKtadyjb9m2aggujattt7k2b0gy` FOREIGN KEY (`sender_id`) REFERENCES `account` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;