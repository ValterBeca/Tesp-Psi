-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 30-Nov-2024 às 20:07
-- Versão do servidor: 10.4.14-MariaDB
-- versão do PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `restaurante`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `migration`
--

CREATE TABLE `migration` (
  `version` varchar(180) NOT NULL,
  `apply_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `migration`
--

INSERT INTO `migration` (`version`, `apply_time`) VALUES
('m000000_000000_base', 1669757634),
('m130524_201442_init', 1669757638),
('m190124_110200_add_verification_token_column_to_user_table', 1669757638);

-- --------------------------------------------------------

--
-- Estrutura da tabela `prato`
--

CREATE TABLE `prato` (
  `id` int(255) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `preco` int(255) DEFAULT NULL,
  `disponivel` tinyint(1) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `prato`
--

INSERT INTO `prato` (`id`, `nome`, `descricao`, `preco`, `disponivel`, `data_criacao`) VALUES
(1, 'carne', NULL, 15, NULL, '2023-10-24'),
(2, 'peixe', NULL, NULL, NULL, '2022-11-24'),
(4, 'Esparguete', 'massa', 10, NULL, '2023-11-24'),
(5, 'Cozido à Portuguesa', 'Cozido à Portuguesa', 20, NULL, '2023-11-24'),
(10, 'Bacalhau à Brás', 'Bacalhau à Brás', NULL, NULL, '2023-11-28'),
(11, 'Bacalhau Gomes de Sá', 'Bacalhau Gomes de Sá', NULL, NULL, '2023-11-28'),
(12, NULL, NULL, NULL, NULL, '2024-11-23'),
(13, NULL, NULL, NULL, NULL, '2024-11-23'),
(14, NULL, NULL, NULL, NULL, '2024-11-23'),
(15, NULL, NULL, NULL, NULL, '2024-11-23'),
(16, NULL, NULL, NULL, NULL, '2024-11-23'),
(17, 'Esparguete', 'massa', 10, NULL, '2024-11-23'),
(18, 'Esparguete', 'massa', NULL, NULL, '2024-11-23'),
(19, NULL, NULL, NULL, NULL, '2024-11-23'),
(20, NULL, NULL, 12, NULL, '2024-11-23'),
(21, NULL, NULL, 11, NULL, '2024-11-23'),
(22, NULL, NULL, 10, NULL, '2024-11-23'),
(23, 'Esparguete', 'massa', NULL, NULL, '2024-11-23'),
(24, 'Esparguete', 'massa', NULL, NULL, '2024-11-23'),
(25, NULL, NULL, NULL, NULL, '2024-11-25'),
(26, NULL, NULL, NULL, NULL, '2024-11-25'),
(27, 'Bolonhesa', 'massa', 20, NULL, '2024-11-25'),
(28, 'Feijoada', 'Feijoada à Portuguesa', NULL, NULL, '2024-11-25');

-- Sugestão dada por Rafael Reis (2231432)
--
-- Acionadores `prato`
--
DELIMITER $$
CREATE TRIGGER `before_insert_prato` BEFORE INSERT ON `prato` FOR EACH ROW BEGIN  
    IF NEW.`data_criacao` IS NULL THEN    
        SET NEW.`data_criacao` = CURRENT_DATE;  
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `auth_key` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password_reset_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `status` smallint(6) NOT NULL DEFAULT 10,
  `created_at` int(11) NOT NULL,
  `updated_at` int(11) NOT NULL,
  `verification_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `user`
--

INSERT INTO `user` (`id`, `username`, `auth_key`, `password_hash`, `password_reset_token`, `email`, `status`, `created_at`, `updated_at`, `verification_token`) VALUES
(1, 'teste', 'XSZY42e8uqDzWEG3EAKI1M8kRdQiILQr', '$2y$13$wOWcTZfOueqryDuTECjCt.xoO1Dy/Xv/.dS4k9iBMFJxRzTfmlGoK', NULL, 'teste@mail.com', 10, 1701087441, 1701087441, '1p6HOqM8I5E8g95WHpWqhXO8fplbaM7y_1701087441'),
(2, 'teste2', 'U5pOC8P4K30E2rIBQQUPfcveRH1GFm0s', '$2y$13$JDWgiPLYOXuVLZ8ZEtEObuLRlFbAgqxrwsncay74RNFO5UCYenVxy', NULL, 'teste2@mail.com', 10, 1701100578, 1701100578, 'hfkTsUyzDUWu-jpifcHkXQtmZ3tW4Vbn_1701100578'),
(3, 'teste3', 'nhM4WWGw2gqZ-DJiA1Pv03sGFwk8MR0Q', '$2y$13$YsqyAp13CjKf1p2UT4LNMeiJyQt7muEkomerQEwccnMfMeOAd707q', NULL, 'teste3@mail.com', 9, 1701289777, 1701289777, 'U7N_TxQMBXJF2ZMlWfOq-6ZdLPiuWt88_1701289777'),
(4, 'teste4', 'C91k5vc2-n0AbqXxpgF-V_PQY137SGwJ', '$2y$13$dk223J1L1Emb6qOY5ucAOOetU/GykD3b8WxWsyHHT2vKkMN.dT5Wu', NULL, 'teste4@mail.com', 9, 1730752076, 1730752076, 'SerCBYQj9zf3UkAbayjfCJWbMjzgBF8c_1730752076'),
(5, 'teste5', 'ZIRpiH5jeEaF0kNAMAVZyWvn8aXDex14', '$2y$13$RoddY41i8ADLvhxOAivXeOvUv/yOYn17oyA3l1.uSllZ7k4/KgBUm', NULL, 'teste5@mail.com', 10, 1731339125, 1731339125, 'coztBsHgi8uDmAuHqn7nH-O3jv0DmqHr_1731339125'),
(6, 'teste6', 'HDMDzS6aFc3kECTf0J9z3bcB62xDlAU6', '$2y$13$2CuUXAWI6VQXEVXnDNktaOLm5pqgxONeq.KMS7xBaFajK3ZOAnnHG', NULL, 'teste6@mail.com', 10, 1731353447, 1731353447, '3-whKni_3Nfdhk_y-Kfdjz-eTimvHggM_1731353447');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `migration`
--
ALTER TABLE `migration`
  ADD PRIMARY KEY (`version`);

--
-- Índices para tabela `prato`
--
ALTER TABLE `prato`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `password_reset_token` (`password_reset_token`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `prato`
--
ALTER TABLE `prato`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de tabela `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
