CREATE DATABASE IF NOT EXISTS foruml; USE foruml;
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 27 avr. 2026 à 20:16
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `foruml`
--

-- --------------------------------------------------------

--
-- Structure de la table `bad_word`
--

CREATE TABLE `bad_word` (
  `id` bigint(20) NOT NULL,
  `added_at` datetime(6) DEFAULT NULL,
  `word` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id`, `created_at`, `description`, `name`) VALUES
(2, NULL, 'ici tu peux dire quelque chose ici', 'Discussions Generales'),
(3, NULL, 'ici questionne any question about the courses', 'questions academiques'),
(4, NULL, 'you can ask about any events', 'Events et activity');

-- --------------------------------------------------------

--
-- Structure de la table `reply`
--

CREATE TABLE `reply` (
  `id` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `topic_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reply`
--

INSERT INTO `reply` (`id`, `content`, `created_at`, `topic_id`) VALUES
(2, 'because they cant do nothnig', NULL, 2),
(3, 'oh thats cool', NULL, 3),
(7, 'yatek boussa', NULL, 8),
(8, 'you are stupid', NULL, 2),
(9, 'oknhgrd', NULL, 6),
(10, 'you are stupid', NULL, 6),
(12, 'yes thats correct', '2026-03-05 03:09:10.000000', 9),
(13, 'niceee', '2026-03-05 03:19:28.000000', 3),
(14, 'nice', '2026-03-05 13:20:57.000000', 14);

-- --------------------------------------------------------

--
-- Structure de la table `report`
--

CREATE TABLE `report` (
  `id` bigint(20) NOT NULL,
  `reason` enum('HARASSMENT','INAPPROPRIATE','OTHER','SPAM') DEFAULT NULL,
  `reported_at` datetime(6) DEFAULT NULL,
  `status` enum('DISMISSED','PENDING','REVIEWED') DEFAULT NULL,
  `reply_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `report`
--

INSERT INTO `report` (`id`, `reason`, `reported_at`, `status`, `reply_id`) VALUES
(1, 'INAPPROPRIATE', '2026-03-04 21:12:32.000000', 'PENDING', 8),
(2, 'INAPPROPRIATE', '2026-03-04 21:12:33.000000', 'PENDING', 8),
(3, 'INAPPROPRIATE', '2026-03-04 21:12:49.000000', 'PENDING', 8),
(4, 'INAPPROPRIATE', '2026-03-04 21:12:50.000000', 'PENDING', 8),
(5, 'INAPPROPRIATE', '2026-03-04 21:12:50.000000', 'PENDING', 8),
(6, 'INAPPROPRIATE', '2026-03-04 21:12:50.000000', 'PENDING', 8),
(7, 'HARASSMENT', '2026-03-05 03:12:50.000000', 'PENDING', 12),
(8, 'INAPPROPRIATE', '2026-03-05 03:12:59.000000', 'PENDING', 12),
(9, 'HARASSMENT', '2026-04-25 14:59:43.000000', 'PENDING', 2),
(10, 'HARASSMENT', '2026-04-25 14:59:44.000000', 'PENDING', 2),
(11, 'HARASSMENT', '2026-04-25 14:59:45.000000', 'PENDING', 2);

-- --------------------------------------------------------

--
-- Structure de la table `topic`
--

CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `author_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `topic`
--

INSERT INTO `topic` (`id`, `content`, `created_at`, `title`, `category_id`, `author_email`) VALUES
(2, 'why too much time in ramadan', '2026-02-24 21:10:11.000000', 'discussion ', 2, NULL),
(3, 'there is active evenment in Mars stay tunned', '2026-02-26 11:15:05.000000', 'evenement disponible apres mars', 2, NULL),
(4, 'add me ', '2026-03-03 21:02:41.000000', 'topic 1', 2, NULL),
(6, 'sdfgrhyjk', '2026-03-03 23:08:47.000000', 'topic3', 2, 'hayfajbili004@gmail.com'),
(8, 'joyeux anniversaire ', '2026-03-03 23:21:11.000000', 'aid sa3id', 2, '4dbccea1e0@emailax.pro'),
(9, 'I heard that english teacher not coming ', '2026-03-05 03:08:10.000000', 'English lessons are postponed?', 3, 'a59ef5836c@emailax.pro'),
(10, 'i heard that its windy here', '2026-03-05 12:44:20.000000', 'how is the weather in school', 2, '764c6b004d@emailax.pro'),
(14, 'fahem nidhal is here', '2026-03-05 13:19:48.000000', 'nidhal', 2, '764c6b004d@emailax.pro'),
(15, 'yhjklm', '2026-03-05 13:23:14.000000', 'nieee', 2, '764c6b004d@emailax.pro');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bad_word`
--
ALTER TABLE `bad_word`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKaj1oke951q1jtqw1f7rn4c0lq` (`word`);

--
-- Index pour la table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK46ccwnsi9409t36lurvtyljak` (`name`);

--
-- Index pour la table `reply`
--
ALTER TABLE `reply`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgf3tac9fa0n9vf62c7eh5eb4y` (`topic_id`);

--
-- Index pour la table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrxmqns3s8l98cumv1xa784h6j` (`reply_id`);

--
-- Index pour la table `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8n7r9utm8sjpdfstb4wcqd7qj` (`category_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bad_word`
--
ALTER TABLE `bad_word`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `reply`
--
ALTER TABLE `reply`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT pour la table `report`
--
ALTER TABLE `report`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT pour la table `topic`
--
ALTER TABLE `topic`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reply`
--
ALTER TABLE `reply`
  ADD CONSTRAINT `FKgf3tac9fa0n9vf62c7eh5eb4y` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

--
-- Contraintes pour la table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `FKrxmqns3s8l98cumv1xa784h6j` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`id`);

--
-- Contraintes pour la table `topic`
--
ALTER TABLE `topic`
  ADD CONSTRAINT `FK8n7r9utm8sjpdfstb4wcqd7qj` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
