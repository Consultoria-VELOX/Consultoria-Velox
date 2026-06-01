CREATE DATABASE db_velox;

USE db_velox;

CREATE TABLE tb_usuarios (
id_usuario INT PRIMARY KEY AUTO_INCREMENT,
nome_usuario VARCHAR(100) NOT NULL,
sobrenome_usuario VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
telefone CHAR(20) NOT NULL,
senha VARCHAR(255) NOT NULL
);

CREATE TABLE tb_cargo(
id_cargo INT PRIMARY KEY,
nome_cargo VARCHAR(15) NOT NULL
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
    id_veiculo INT NULL,
    tipo_servico ENUM('Compra','Venda','Consultoria') NOT NULL,
    data_preferida VARCHAR(15) NOT NULL,
    periodo ENUM('Manhã', 'Tarde', 'Noite') NOT NULL,
    descricao_problema TEXT NOT NULL,
    status_ticket ENUM('Aberto', 'Em Andamento', 'Resolvido', 'Cancelado') NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuarios(id_usuario),
    FOREIGN KEY (id_veiculo) REFERENCES tb_estoque_veiculos(id_veiculo)
);

INSERT INTO tb_cargo VALUES 
("1", "Cliente"),
("2", "Gestor"),
("3", "Administrador");

INSERT INTO tb_usuarios (nome_usuario, sobrenome_usuario, email, telefone, senha)
VALUES ('Admin', 'Velox', 'admin@velox.com', '(00) 00000-0000', '123');

INSERT INTO tb_usuario_cargo VALUES (1, 3);

INSERT INTO tb_estoque_veiculos (marca_veiculo, modelo_veiculo, ano, placa_veiculo, descricao, disponivel)
VALUES ('Chevrolet', 'Corsa', '2008', 'AAAAAAA', 'Em perfeito estado', true);

SELECT*FROM tb_usuarios;
SELECT*FROM tb_usuario_cargo;
SELECT*FROM tb_tickets;