package org.example;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class velox {
    //Criação de constantes que armazenam credenciais do banco de dados
    public static String URL = "jdbc:mysql://localhost:3306/db_velox";
    public static String USER = "root";
    public static String PASS = "123123";
    public static Boolean logado = false;
    public static String cargo = "Cliente";


    public static void main(String[] args) {
        String[] opcoes = {"Cadastrar-se", "Logar-se"};
        int respostaRegistroLogin = JOptionPane.showOptionDialog(
                null,
                "Seja bem vindo!, Escolha uma opção",
                "Cadastro/Login",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (respostaRegistroLogin == 0){
            JOptionPane.showMessageDialog(null, "Cadastro selecionado", "Velox", JOptionPane.INFORMATION_MESSAGE );
            cadastrarUsuario();
        } else if (respostaRegistroLogin == 1){
            JOptionPane.showMessageDialog(null, "Login selecionado", "Velox", JOptionPane.INFORMATION_MESSAGE);
            loginUsuario();
        }//Fim if else
    }// Fim do método main

    public static void cadastrarUsuario(){
        //Criação de campos para o formulário
        JTextField campoNome = new JTextField();
        JTextField campoSobrenome = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoTelefone = new JTextField();
        JTextField campoSenha = new JTextField();

        //Criação das variaveis que armazenaram as respostas do formulario
        String nome = null;
        String sobrenome = null;
        String email = null;
        String telefone = null;
        String senha = null;
        int idUsuario = 0;

        //Cria o formulário
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));

        formulario.add(new JLabel("Nome:"));//Adiciona um rótulo para o campo
        formulario.add(campoNome);//Adiciona o campo
        formulario.add(new JLabel("Sobrenome:"));
        formulario.add(campoSobrenome);
        formulario.add(new JLabel("E-mail:"));
        formulario.add(campoEmail);
        formulario.add(new JLabel("Telefone:"));
        formulario.add(campoTelefone);
        formulario.add(new JLabel("Senha:"));
        formulario.add(campoSenha);

        //Enquanto todas os campos não forem preenchidos, o formulário continuará aparecendo
        while(true) {
            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    formulario,
                    "Cadastrar-se",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (resposta != JOptionPane.OK_OPTION) {
                break;
            }

            nome = campoNome.getText();
            sobrenome = campoSobrenome.getText();
            email = campoEmail.getText();
            telefone = campoTelefone.getText();
            senha = campoSenha.getText();

            if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()){
                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }//Fim do if
            break;
        }//Fim do while

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
            System.out.println("Conectado com sucesso!");

            String sql1 = "INSERT INTO tb_usuarios(nome_usuario, sobrenome_usuario, email, telefone, senha) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql1);

            ps.setString(1, nome);
            ps.setString(2, sobrenome);
            ps.setString(3, email);
            ps.setString(4, telefone);
            ps.setString(5, senha);

            int linhasAlteradas = ps.executeUpdate();

            if(linhasAlteradas > 0){
                JOptionPane.showMessageDialog(
                        null,
                        "Cadastrado com sucesso",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }else {
                JOptionPane.showMessageDialog(
                        null,
                        "Erro ao cadastrar",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            String sql2 = ("SELECT id_usuario FROM tb_usuarios WHERE email = ?");

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, email);

            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                idUsuario = rs.getInt("id_usuario");
            }

            String sql3 = ("INSERT INTO tb_usuario_cargo VALUES(?, ?)");

            PreparedStatement ps3 = conn.prepareStatement(sql3);

            ps3.setInt(1, idUsuario);
            ps3.setInt(2, 1);

            ps3.executeUpdate();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }//Fim do try catch

    }//Fim do método cadastrarUsuario();

    public static void loginUsuario(){
        JTextField campoEmail = new JTextField();
        JTextField campoSenha = new JTextField();

        String emailFornecido = null;
        String senhaFornecida = null;

        JPanel formularioLogin = new JPanel(new GridLayout(2, 2, 10, 10));
        formularioLogin.add(new JLabel("E-mail:"));
        formularioLogin.add(campoEmail);
        formularioLogin.add(new JLabel("Senha:"));
        formularioLogin.add(campoSenha);

        while(true){
            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    formularioLogin,
                    "Logar-se",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if(resposta != JOptionPane.OK_OPTION){
                JOptionPane.showMessageDialog(null, "Encerrando...");
                break;
            }//Fim if

            emailFornecido = campoEmail.getText();
            senhaFornecida = campoSenha.getText();

            if(emailFornecido.isEmpty() || senhaFornecida.isEmpty()){
                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }//Fim if else
            break;
        }//Fim while

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)){
            String sql = "SELECT c.nome_cargo FROM tb_usuarios u " +
                    "JOIN tb_usuario_cargo uc ON u.id_usuario = uc.id_usuario " +
                    "JOIN tb_cargo c ON uc.id_cargo = c.id_cargo " +
                    "WHERE email = ? AND senha = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, emailFornecido);
            ps.setString(2, senhaFornecida);

            ResultSet rs = ps.executeQuery();


            if  (rs.next()) {
                logado = true;
                cargo = rs.getString("nome_cargo");
                System.out.println(cargo);
                JOptionPane.showMessageDialog(
                        null,
                        "Logado com sucesso!!",
                        "Sucesso!",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                logado = false;
                JOptionPane.showMessageDialog(
                        null,
                        "E-mail ou senha incorretos!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }//Fim if else


        }catch(SQLException e){
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao conectar ao banco de dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }//Fim try catch
    }//Fim método loginUsuario()
}//Fim da classe velox
