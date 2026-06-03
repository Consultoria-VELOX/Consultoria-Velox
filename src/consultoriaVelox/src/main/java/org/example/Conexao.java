package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    //Criação de constantes que armazenam credenciais do banco de dados
    public static final String URL = "jdbc:mysql://localhost:3306/db_velox";
    public static final String USER = "root";
    public static final String PASS = "123123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
