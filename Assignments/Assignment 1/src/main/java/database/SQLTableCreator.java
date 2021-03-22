package database;

import static database.Constants.Tables.*;
public class SQLTableCreator {
    public static String getTableCreateSQL(String table){
        return switch (table) {
            case CLIENT -> "CREATE TABLE `client` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `name` varchar(500) NOT NULL," +
                    "  `idcard` varchar(8) NOT NULL," +
                    "  `cnp` varchar(13) NOT NULL," +
                    "  `address` varchar(500) NOT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ACCOUNT -> "CREATE TABLE `account` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `clientid` int NOT NULL," +
                    "  `accountNumber` varchar(45) NOT NULL," +
                    "  `amount` float NOT NULL DEFAULT '0'," +
                    "  `creationdate` date NOT NULL," +
                    "  `typeid` int NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `clientKey_idx` (`clientid`)," +
                    "  KEY `typeKey_idx` (`typeid`)," +
                    "  CONSTRAINT `clientKey` FOREIGN KEY (`clientid`) REFERENCES `client` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  CONSTRAINT `typeKey` FOREIGN KEY (`typeid`) REFERENCES `account_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case USER -> "CREATE TABLE `user` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `username` varchar(500) NOT NULL UNIQUE," +
                    "  `password` varchar(500) NOT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ROLE -> "CREATE TABLE `role` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `role` varchar(50) NOT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case RIGHT -> "CREATE TABLE `right` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `right` varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ROLE_RIGHT -> "CREATE TABLE `role_right` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `idrole` int NOT NULL," +
                    "  `idright` int NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `role_key_idx` (`idrole`)," +
                    "  KEY `right_key_idx` (`idright`)," +
                    "  CONSTRAINT `right_key` FOREIGN KEY (`idright`) REFERENCES `right` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  CONSTRAINT `role_key` FOREIGN KEY (`idrole`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case USER_ROLE ->"CREATE TABLE `user_role` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `userid` int NOT NULL," +
                    "  `roleid` int NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `user_key_idx` (`userid`)," +
                    "  KEY `role_key_idx` (`roleid`)," +
                    "  CONSTRAINT `user_key` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE," +
                    "  CONSTRAINT `userrole_key` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ACTIVITY -> "CREATE TABLE `activity` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `user` int NOT NULL," +
                    "  `description` varchar(600) NOT NULL," +
                    "  `eventTime` datetime NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  KEY `fk_activity_1_idx` (`user`)," +
                    "  CONSTRAINT `fk_activity_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case ACCOUNT_TYPE -> "CREATE TABLE `account_type` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `type` varchar(45) NOT NULL," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            case UTILITIES -> "CREATE TABLE `utilities` (" +
                    "  `id` int NOT NULL AUTO_INCREMENT," +
                    "  `name` varchar(45) NOT NULL," +
                    "  `validationRegex` varchar(200) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE KEY `name_UNIQUE` (`name`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
            default -> "";
        };
    }
}
