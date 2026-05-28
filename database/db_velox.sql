CREATE DATABASE db_velox;

USE db_velox;

CREATE TABLE tb_clientes (
id_cliente INT PRIMARY KEY AUTO_INCREMENT,
nome_cliente VARCHAR(100) NOT NULL,
sobrenome_cliente VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
telefone CHAR(14) UNIQUE NOT NULL,
senha VARCHAR(255) NOT NULL,
funcao ENUM('cliente', 'admin') NOT NULL,
criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_estoque_veiculos (
	id_veiculo INT PRIMARY KEY AUTO_INCREMENT,
    marca_veiculo VARCHAR(20) NOT NULL,
    modelo_veiculo VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    placa_veiculo CHAR(7) NOT NULL UNIQUE,
    descricao TEXT NOT NULL,
    imagem_veiculo LONGBLOB,
    disponivel BOOLEAN NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_tickets(
	id_ticket INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_veiculo INT NOT NULL,
    tipo_servico ENUM('Compra','Venda','Consultoria') NOT NULL,
    data_preferida DATE NOT NULL,
    periodo ENUM('Manhã', 'Tarde', 'Noite') NOT NULL,
    descricao_problema TEXT NOT NULL,
    status_ticket ENUM('Aberto', 'Em Andamento', 'Resolvido', 'Cancelado') NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES tb_clientes(id_cliente),
    FOREIGN KEY (id_veiculo) REFERENCES tb_estoque_veiculos(id_veiculo)
);