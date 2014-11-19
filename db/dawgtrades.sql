#
# this SQL file creates the schema for the clubs database
#
# remove the existing tables
#
DROP TABLE IF EXISTS membership;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS attribute;
DROP TABLE IF EXISTS attribute_type;
DROP TABLE IF EXISTS auction;
DROP TABLE IF EXISTS bid;
DROP TABLE IF EXISTS registered_user;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS experience_report;


CREATE TABLE `membership` (
  `id` int unsigned primary key auto_increment,
  `price` float DEFAULT NULL,
  `date` date DEFAULT NULL,
) ENGINE=InnoDB;

CREATE TABLE `category` (
  `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `parent_id` INT UNSIGNED,
   FOREIGN KEY (parent_id) REFERENCES category(id) 
  
) ENGINE=InnoDB;


CREATE TABLE `attribute_type` (
  `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `category_id` INT UNSIGNED,
   FOREIGN KEY (category_id) REFERENCES category(id) 
  
) ENGINE=InnoDB;

CREATE TABLE `registered_user` (
	`id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`first_name` VARCHAR(255) NOT NULL,
	`last_name` VARCHAR(255) NOT NULL,
	`password` VARCHAR(255) NOT NULL,
	`email` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255) NOT NULL,
	`canText` INT UNSIGNED NOT NULL,
	`isAdmin` INT UNSIGNED NOT NULL
) ENGINE=InnoDB;


CREATE TABLE `experience_report` (
	`id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`rating` INT UNSIGNED NOT NULL,
	`report` VARCHAR(255) NOT NULL,
	`reviewer_id` INT UNSIGNED NOT NULL,
	`reviewed_id` INT UNSIGNED NOT NULL,
	`date` DATE NOT NULL,
	FOREIGN KEY (reviewer_id) REFERENCES registered_user(id),
	FOREIGN KEY (reviewed_id) REFERENCES registered_user(id),
	FOREIGN KEY (attribute_type_id) REFERENCES attribute_type(id),
	CONSTRAINT rating_ck CHECK (rating IN (1, 2, 3, 4,5))
) ENGINE=InnoDB;


CREATE TABLE `item` (
  `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  `category_id` INT UNSIGNED,
   FOREIGN KEY (category_id) REFERENCES category(id),
   FOREIGN KEY (user_id) REFERENCES registered_user(id) 
  
) ENGINE=InnoDB;

CREATE TABLE `attribute` (
	`id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`value` VARCHAR(255) NOT NULL,
	`item_id` INT UNSIGNED NOT NULL,
	`attribute_type_id` INT UNSIGNED NOT NULL,
	FOREIGN KEY (item_id) REFERENCES item(id),
	FOREIGN KEY (attribute_type_id) REFERENCES attribute_type(id) 

) ENGINE=InnoDB;

CREATE TABLE `auction` (
	`id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`min_price` FLOAT NOT NULL,
	`expiration` DATE NOT NULL,
	`item_id` INT UNSIGNED NOT NULL,
	FOREIGN KEY (item_id) REFERENCES item(id)
	
) ENGINE=InnoDB;

CREATE TABLE `bid` (
	`id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`amount` FLOAT NOT NULL,
	`user_id` INT UNSIGNED NOT NULL,
	`auction_id` INT UNSIGNED NOT NULL,
	FOREIGN KEY (auction_id) REFERENCES auction(id);
) ENGINE=InnoDB;



