-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: יוני 13, 2022 בזמן 05:35 PM
-- גרסת שרת: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ex4`
--

-- --------------------------------------------------------

--
-- מבנה טבלה עבור טבלה `book`
--

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL,
  `discount` double NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- הוצאת מידע עבור טבלה `book`
--

INSERT INTO `book` (`id`, `discount`, `image`, `name`, `price`, `quantity`) VALUES
(0, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4087/9781408714119.jpg', 'Happy-Go-Lucky', 64.29, 10),
(1, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4711/9781471156267.jpg', 'It Ends With Us: The most heartbreaking novel you\'ll ever read', 41.44, 10),
(2, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0083/9780008389666.jpg', 'Nick and Charlie', 44.41, 10),
(3, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/0075/9780007559220.jpg', 'Solitaire', 49.23, 8),
(4, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4087/9781408712450.jpg', 'The Murder Book', 66.35, 8),
(5, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4063/9781406377644.jpg', 'Ready for Spaghetti: Funny Poems for Funny Kids', 59.01, 6),
(6, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4087/9781408714119.jpg', 'Happy-Go-Lucky', 64.29, 10),
(7, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/4091/9781409188506.jpg', 'Cat And Mouse', 63.54, 6),
(8, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/6385/9781638582106.jpg', 'Heaven Official\'s Blessing: Tian Guan Ci Fu (Novel) Vol. 3', 60.68, 6),
(9, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/8453/9781845339906.jpg', 'White Heat 25', 101.91, 15),
(10, 0, 'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9781/7860/9781786071958.jpg', 'Sweet Bean Paste', 41.02, 15);

--
-- Indexes for dumped tables
--

--
-- אינדקסים לטבלה `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
