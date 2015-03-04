-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 01, 2015 at 11:20 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `icare`
--

-- --------------------------------------------------------

--
-- Table structure for table `doctors_profile`
--

CREATE TABLE IF NOT EXISTS `doctors_profile` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `specialization` varchar(20) NOT NULL,
  `work_address` varchar(20) NOT NULL,
  `contact_no` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `profile_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `doctors_profile`
--

INSERT INTO `doctors_profile` (`id`, `name`, `specialization`, `work_address`, `contact_no`, `email`, `profile_id`) VALUES
(1, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 1),
(2, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 1),
(3, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 1),
(4, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 1),
(5, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 1),
(6, 'Enayet ullah', 'Heart Specialist', 'Mirpur', '0166555', 'ishtiaqn2o@gmail.com', 2);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `date_of_birth` date NOT NULL,
  `height` float NOT NULL,
  `weight` float NOT NULL,
  `gender` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`id`, `name`, `date_of_birth`, `height`, `weight`, `gender`) VALUES
(1, 'Ishtiaq', '2015-05-13', 5.5, 70, 'male'),
(2, 'Ishtiaq', '1992-07-14', 5.5, 70, 'male'),
(3, 'Nazmul', '1992-05-14', 5.7, 85, 'male'),
(4, 'sdfs', '2015-02-12', 5.5, 70, 'male'),
(5, 'Isht', '2015-05-13', 5.5, 70, 'male'),
(6, 'Isht', '2015-05-13', 5.5, 70, 'male');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
