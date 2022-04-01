-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 01 avr. 2022 à 23:52
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `bddinscription`
--

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

CREATE TABLE `association` (
  `id_Asso` int(11) NOT NULL,
  `Association` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`id_Asso`, `Association`) VALUES
(1, 'Musique'),
(2, 'Multi-Sport'),
(3, 'Art Plastiques');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `Pseudo` varchar(30) NOT NULL,
  `Mdp` varchar(30) NOT NULL,
  `Disponibilité` varchar(70) NOT NULL,
  `Niveau` varchar(30) NOT NULL,
  `Commentaire` varchar(300) NOT NULL,
  `FK_Asso` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`Pseudo`, `Mdp`, `Disponibilité`, `Niveau`, `Commentaire`, `FK_Asso`) VALUES
('admin', 'admin', 'lundi mardi mercredi jeudi ven', 'haut', 'Fan de l\'om je sais pas les noms des joueurs mais allez l\'om le foot c\'est vraiment un sport de qualité supérieure c\'est vraiment très intellectuel, vraiment les stratégies employées par l\'entraineur et mises en place par les joueurs dépassent l\'entendemant de nous autres mortels.', 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `association`
--
ALTER TABLE `association`
  ADD PRIMARY KEY (`id_Asso`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`Pseudo`),
  ADD KEY `Association` (`FK_Asso`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `association`
--
ALTER TABLE `association`
  MODIFY `id_Asso` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `Association` FOREIGN KEY (`FK_Asso`) REFERENCES `association` (`id_Asso`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
