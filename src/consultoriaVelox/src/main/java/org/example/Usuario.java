package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    public static void cadastrarUsuario() {
        //Criação de campos para o formulário
        JTextField campoNome = new JTextField();
        JTextField campoSobrenome = new JTextField();
        JTextField campoEmail = new JTextField();
        JTextField campoTelefone = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        //Criação das variaveis que armazenaram as respostas do formulario
        String nome = null;
        String sobrenome = null;
        String email = null;
        String telefone = null;
        String senha = null;
        int idUsuario = 0;
        int resposta;

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
        while (true) {
            resposta = JOptionPane.showConfirmDialog(
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
            senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() || sobrenome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }//Fim do if
            try (Connection conn = Conexao.conectar()){
                String sql = "SELECT * FROM tb_usuarios WHERE email = ?";

                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, email);

                ResultSet rs = ps.executeQuery();

                //Se alguma linha retornar
                if (rs.next()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "O email informado já esta cadastrado!",
                            "Erro!",
                            JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }// Fim if else
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            break;
        }//Fim do while

        if (resposta == JOptionPane.OK_OPTION) {
            try (Connection conn = Conexao.conectar();) {
                String sql1 = "INSERT INTO tb_usuarios(nome_usuario, sobrenome_usuario, email, telefone, senha) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement ps = conn.prepareStatement(sql1);

                ps.setString(1, nome);
                ps.setString(2, sobrenome);
                ps.setString(3, email);
                ps.setString(4, telefone);
                ps.setString(5, senha);

                int linhasAlteradas = ps.executeUpdate();

                if (linhasAlteradas > 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Cadastrado com sucesso",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
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


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }//Fim do try catch
        } else {
            JOptionPane.showMessageDialog(null, "Cancelando...", "Velox", JOptionPane.INFORMATION_MESSAGE);
        }//Fim do if else de resposta
    }//Fim do método cadastrarUsuario();

    public static void loginUsuario() {
        JTextField campoEmail = new JTextField();
        JPasswordField campoSenha = new JPasswordField();

        String emailFornecido = null;
        String senhaFornecida = null;

        JPanel formularioLogin = new JPanel(new GridLayout(2, 2, 10, 10));
        formularioLogin.add(new JLabel("E-mail:"));
        formularioLogin.add(campoEmail);
        formularioLogin.add(new JLabel("Senha:"));
        formularioLogin.add(campoSenha);

        while (Sessao.logado == false) {
            int resposta = JOptionPane.showConfirmDialog(
                    null,
                    formularioLogin,
                    "Logar-se",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );


            if (resposta != JOptionPane.OK_OPTION) {
                break;
            }//Fim if

            emailFornecido = campoEmail.getText();
            senhaFornecida = new String (campoSenha.getPassword());

            if (emailFornecido.isEmpty() || senhaFornecida.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Todos os campos são obrigatórios!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                continue;
            }//Fim if else

            if (resposta == JOptionPane.OK_OPTION) {
                try (Connection conn = Conexao.conectar();) {
                    String sql = "SELECT c.nome_cargo, u.id_usuario, u.nome_usuario, u.sobrenome_usuario FROM tb_usuarios u " +
                            "JOIN tb_usuario_cargo uc ON u.id_usuario = uc.id_usuario " +
                            "JOIN tb_cargo c ON uc.id_cargo = c.id_cargo " +
                            "WHERE email = ? AND senha = ?";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(1, emailFornecido);
                    ps.setString(2, senhaFornecida);

                    ResultSet rs = ps.executeQuery();


                    if (rs.next()) {
                        Sessao.logado = true;
                        Sessao.cargo = rs.getString("nome_cargo");
                        Sessao.idUsuario = rs.getInt("id_usuario");
                        Sessao.nomeUsuario = rs.getString("nome_usuario") + " " + rs.getString("sobrenome_usuario");
                        JOptionPane.showMessageDialog(
                                null,
                                "Logado com sucesso!!",
                                "Sucesso!",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        Sessao.logado = false;
                        JOptionPane.showMessageDialog(
                                null,
                                "E-mail ou senha incorretos!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }//Fim if else


                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Erro ao conectar ao banco de dados: " + e.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                    );
                }//Fim try catch
            } else {
                JOptionPane.showMessageDialog(null, "Cancelando...", "Velox", JOptionPane.INFORMATION_MESSAGE);
            }//Fim do if else escolha
        }//Fim do while logado
    }//Fim método loginUsuario()
}
