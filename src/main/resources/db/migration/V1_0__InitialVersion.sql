CREATE TABLE `catalogue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `last_modified` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `catalogue_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `last_modified` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `catalogue_products` (
    `catalogue_id` bigint(20) NOT NULL,
    `products_id` bigint(20) NOT NULL,
    PRIMARY KEY (`catalogue_id`,`products_id`),
    UNIQUE KEY `UK_4s59udvhh41bb080komfliequ` (`products_id`),
    CONSTRAINT `FK9g60jy860vtb74xbee6bv4jjq` FOREIGN KEY (`catalogue_id`) REFERENCES `catalogue` (`id`),
    CONSTRAINT `FKi0jpejmigqr187yuougwsh674` FOREIGN KEY (`products_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
