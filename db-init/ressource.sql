CREATE DATABASE IF NOT EXISTS ressource; USE ressource;
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 27 avr. 2026 à 20:38
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
-- Base de données : `ressource`
--

-- --------------------------------------------------------

--
-- Structure de la table `resources`
--

CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic_id` bigint(20) DEFAULT NULL,
  `type` enum('LINK','PDF','VIDEO') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `resources`
--

INSERT INTO `resources` (`id`, `created_at`, `description`, `title`, `topic_id`, `type`, `url`, `user_id`, `updated_at`) VALUES
(9, '2026-04-15 21:16:43.000000', 'fcfcfcfgghgh', 'dfgrtyu', 10, 'PDF', 'dthghjhjkjk', NULL, NULL),
(10, '2026-04-15 21:17:13.000000', 'gvjhbhkjjkk', 'nidhal', 10, 'VIDEO', 'vgfbhjkljkjk', NULL, NULL),
(12, '2026-04-15 21:51:05.000000', 'ok cest ca', 'bibliotheque books english', 2, 'PDF', 'https://www.youtube.com/embed/dQw4w9WgXcQ', NULL, '2026-04-16 13:36:34.000000'),
(14, '2026-04-16 00:15:54.000000', 'learning video', 'bibliotheque', 9, 'VIDEO', 'https://youtu.be/pzaBnp1FoZ8', NULL, NULL),
(17, '2026-04-16 13:31:37.000000', 'cette video vous aidez a comprendre mieux les bases', 'video explicatif de spring', 2, 'VIDEO', 'https://www.youtube.com/watch?v=7wfYIMyS_dI&list=RD7wfYIMyS_dI&start_radio=1', NULL, NULL),
(18, '2026-04-16 13:40:30.000000', 'Hey you can check this ', 'England History', 9, 'PDF', 'https://historyofeconomicthought.mcmaster.ca/ranke/HistEnglandv1.pdf', NULL, NULL),
(19, '2026-04-25 15:07:20.000000', 'nidhdalalala', 'nidhal', 4, 'VIDEO', 'nidhal', NULL, NULL),
(20, '2026-04-27 17:40:44.000000', 'ert', 'fgbhujikol', 4, 'VIDEO', 'erftg', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `resource_likes`
--

CREATE TABLE `resource_likes` (
  `id` bigint(20) NOT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `resources`
--
ALTER TABLE `resources`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `resource_likes`
--
ALTER TABLE `resource_likes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `resources`
--
ALTER TABLE `resources`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `resource_likes`
--
ALTER TABLE `resource_likes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
