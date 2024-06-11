/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.20-log : Database - bride_and_groom
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bride_and_groom` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bride_and_groom`;

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `design_id` int(11) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  `status` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `bookings` */

insert  into `bookings`(`booking_id`,`user_id`,`design_id`,`date_time`,`status`) values (1,1,1,'2021-02-03 12:37:46','delivered');

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `designer_id` int(11) DEFAULT NULL,
  `category_name` varchar(111) DEFAULT NULL,
  `description` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `categories` */

insert  into `categories`(`category_id`,`designer_id`,`category_name`,`description`) values (1,1,'cat1 ','koo'),(2,2,'br1','br2'),(3,1,'my cat 1','grvf'),(4,1,'cd ','cvcv');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(111) DEFAULT NULL,
  `complaint` varchar(11111) DEFAULT NULL,
  `reply` varchar(1111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

/*Table structure for table `custom_designs` */

DROP TABLE IF EXISTS `custom_designs`;

CREATE TABLE `custom_designs` (
  `custom_design_id` int(11) NOT NULL AUTO_INCREMENT,
  `request_id` int(11) DEFAULT NULL,
  `details` varchar(1111) DEFAULT NULL,
  `material` varchar(111) DEFAULT NULL,
  `price` varchar(111) DEFAULT NULL,
  `image` varchar(111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  `status` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`custom_design_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `custom_designs` */

insert  into `custom_designs`(`custom_design_id`,`request_id`,`details`,`material`,`price`,`image`,`date_time`,`status`) values (1,1,'engnund?','set alle','10000','static/custom_designse53f0c37-99db-47b8-8c41-805156edd0f6chefs-01.jpg','2021-02-03 12:43:06','delivered');

/*Table structure for table `delivery` */

DROP TABLE IF EXISTS `delivery`;

CREATE TABLE `delivery` (
  `delivery_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `book_type` varchar(111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  `status` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`delivery_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `delivery` */

insert  into `delivery`(`delivery_id`,`booking_id`,`book_type`,`date_time`,`status`) values (1,1,'design','2021-02-03 12:38:05','delivered'),(2,1,'custom_design','2021-02-03 12:49:46','delivered'),(3,1,'custom_design','2021-02-03 14:12:00','delivered'),(4,1,'custom_design','2021-02-03 14:13:06','delivered'),(5,1,'custom_design','2021-02-03 14:15:21','delivered'),(6,1,'custom_design','2021-02-03 14:16:09','delivered'),(7,1,'custom_design','2021-02-03 14:16:56','delivered');

/*Table structure for table `designers` */

DROP TABLE IF EXISTS `designers`;

CREATE TABLE `designers` (
  `designer_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `designer_name` varchar(111) DEFAULT NULL,
  `place` varchar(111) DEFAULT NULL,
  `landmark` varchar(111) DEFAULT NULL,
  `pincode` varchar(111) DEFAULT NULL,
  `phone` varchar(111) DEFAULT NULL,
  `email` varchar(111) DEFAULT NULL,
  `status` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`designer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `designers` */

insert  into `designers`(`designer_id`,`login_id`,`designer_name`,`place`,`landmark`,`pincode`,`phone`,`email`,`status`) values (1,2,'koots','prasad','vytilla','123456','2134567890','abc@abc','approved'),(2,3,'britto','kottayam','bus stand','656787','98234535544','britto@gmail.com','approved');

/*Table structure for table `designs` */

DROP TABLE IF EXISTS `designs`;

CREATE TABLE `designs` (
  `design_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `design_name` varchar(111) DEFAULT NULL,
  `model` varchar(111) DEFAULT NULL,
  `material` varchar(111) DEFAULT NULL,
  `details` varchar(1111) DEFAULT NULL,
  `photo` varchar(1111) DEFAULT NULL,
  `price` varchar(111) DEFAULT NULL,
  `made_stock` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`design_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `designs` */

insert  into `designs`(`design_id`,`category_id`,`design_name`,`model`,`material`,`details`,`photo`,`price`,`made_stock`) values (1,1,'des1','1','1','1','static/designes/1ad19f86-cded-41f6-b610-83db5c2ddc1achefs-02.jpg','400','5'),(2,1,'koots','koo','ko','k','static/designes/c5793e08-2e6c-42b2-b2bd-381d0bb29606menu-item-02.jpg','100','5'),(3,2,'brt','br','b','b','static/designes/e8283e34-5ccb-428c-8551-454d1cc5fdb8about-thumb-03.jpg','100','60');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `designer_id` int(11) DEFAULT NULL,
  `feedback` varchar(11111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(111) DEFAULT NULL,
  `password` varchar(111) DEFAULT NULL,
  `usertype` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values (1,'admin','admin','admin'),(2,'designer','designer','designer'),(3,'designer1','designer1','designer');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `book_type` varchar(111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `designer_id` int(11) DEFAULT NULL,
  `rate` varchar(111) DEFAULT NULL,
  `review` varchar(11111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ratings` */

/*Table structure for table `reported` */

DROP TABLE IF EXISTS `reported`;

CREATE TABLE `reported` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `designer_id` int(11) DEFAULT NULL,
  `reason` varchar(1111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `reported` */

/*Table structure for table `requests` */

DROP TABLE IF EXISTS `requests`;

CREATE TABLE `requests` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `designer_id` int(11) DEFAULT NULL,
  `title` varchar(111) DEFAULT NULL,
  `description` varchar(1111) DEFAULT NULL,
  `budget` varchar(111) DEFAULT NULL,
  `estimated_date` varchar(111) DEFAULT NULL,
  `date_time` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `requests` */

insert  into `requests`(`request_id`,`user_id`,`designer_id`,`title`,`description`,`budget`,`estimated_date`,`date_time`) values (1,1,1,'check','it','12000','12 34 56',NULL);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(111) DEFAULT NULL,
  `last_name` varchar(1111) DEFAULT NULL,
  `house_name` varchar(111) DEFAULT NULL,
  `place` varchar(111) DEFAULT NULL,
  `landmark` varchar(111) DEFAULT NULL,
  `pincode` varchar(111) DEFAULT NULL,
  `phone` varchar(111) DEFAULT NULL,
  `email` varchar(111) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`landmark`,`pincode`,`phone`,`email`) values (1,4,'dude','kochi','underworld','edakochi','colony','678987','89564765','dude@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
