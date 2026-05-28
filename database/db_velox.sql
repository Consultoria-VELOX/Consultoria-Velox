CREATE DATABASE db_velox;

USE db_velox;

CREATE TABLE tb_usuarios (
id_usuario INT PRIMARY KEY AUTO_INCREMENT,
nome_usuario VARCHAR(100) NOT NULL,
sobrenome_usuario VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
telefone CHAR(14) UNIQUE NOT NULL,
senha VARCHAR(255) NOT NULL
);

CREATE TABLE tb_cargo(
id_cargo INT PRIMARY KEY,
nome_cargo VARCHAR(10) NOT NULL
);

CREATE TABLE tb_usuario_cargo (
	id_usuario INT NOT NULL,
    id_cargo INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario),
    FOREIGN KEY (id_cargo) REFERENCES tb_cargo(id_cargo)    
);

CREATE TABLE tb_estoque_veiculos (
	id_veiculo INT PRIMARY KEY AUTO_INCREMENT,
    marca_veiculo VARCHAR(20) NOT NULL,
    modelo_veiculo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    placa_veiculo CHAR(7) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,
    disponivel BOOLEAN NOT NULL
);

CREATE TABLE tb_tickets(
	id_ticket INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_veiculo INT NOT NULL,
    tipo_servico ENUM('Compra','Venda','Consultoria') NOT NULL,
    data_preferida DATE NOT NULL,
    periodo ENUM('Manhã', 'Tarde', 'Noite') NOT NULL,
    descricao_problema TEXT NOT NULL,
    status_ticket ENUM('Aberto', 'Em Andamento', 'Resolvido', 'Cancelado') NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario),
    FOREIGN KEY (id_veiculo) REFERENCES tb_estoque_veiculos(id_veiculo)
);