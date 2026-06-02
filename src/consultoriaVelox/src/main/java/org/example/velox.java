package org.example;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class velox {
    //Criação de constantes que armazenam credenciais do banco de dados
    public static final String URL = "jdbc:mysql://localhost:3306/db_velox";
    public static final String USER = "root";
    public static final String PASS = "123123";

    //Criação de váriaveis que serão utilizadas em todo o código
    public static Boolean logado = false;
    public static String cargo = "Cliente";
    public static int idUsuario;
    public static String nomeUsuario;


    public static void main(String[] args) {
        //Cadastro e login
        while (logado == false) {
            String[] opcoes = {"Cadastrar-se", "Logar-se", "Cancelar"};
            int respostaRegistroLogin = JOptionPane.showOptionDialog(
                    null,
                    "Seja bem vindo ao Velox!, Escolha uma opção",
                    "Cadastro/Login",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (respostaRegistroLogin == 0) {
                JOptionPane.showMessageDialog(null, "Cadastro selecionado", "Velox", JOptionPane.INFORMATION_MESSAGE);
                cadastrarUsuario();
            } else if (respostaRegistroLogin == 1) {
                JOptionPane.showMessageDialog(null, "Login selecionado", "Velox", JOptionPane.INFORMATION_MESSAGE);
                loginUsuario();
            } else if (respostaRegistroLogin == 2) {
                JOptionPane.showMessageDialog(null, "Encerrando...", "Velox", JOptionPane.INFORMATION_MESSAGE);
                break;
            }//Fim if else de login
        }// Fim do while logado


        while (logado == true) {
            if (cargo.equals("Cliente")) {
                String[] opcoesCliente = {"Criar Ticket", "Ver Estoque", "Sair"};

                int respostaCliente = JOptionPane.showOptionDialog(
                        null,
                        "Olá " + nomeUsuario + "! Escolha uma opção",
                        "Opções",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoesCliente,
                        opcoesCliente[0]
                );

                if (respostaCliente == 0) {
                    criarTicket();
                }else if (respostaCliente == 1) {
                    System.out.println("Ver estoque");
                }else if (respostaCliente == 2) {
                    logado = false;
                }
            }
        }

    }// Fim do método main

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
            break;
        }//Fim do while

        if (resposta == JOptionPane.OK_OPTION) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Conectado com sucesso!");

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

                String sql3 = ("INSERT INTO tb_usuario_cargo VALUES(?, ?)");

                PreparedStatement ps3 = conn.prepareStatement(sql3);

                ps3.setInt(1, idUsuario);
                ps3.setInt(2, 1);

                ps3.executeUpdate();

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

        while (logado == false) {
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
                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "SELECT c.nome_cargo, u.id_usuario, u.nome_usuario, u.sobrenome_usuario FROM tb_usuarios u " +
                            "JOIN tb_usuario_cargo uc ON u.id_usuario = uc.id_usuario " +
                            "JOIN tb_cargo c ON uc.id_cargo = c.id_cargo " +
                            "WHERE email = ? AND senha = ?";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(1, emailFornecido);
                    ps.setString(2, senhaFornecida);

                    ResultSet rs = ps.executeQuery();


                    if (rs.next()) {
                        logado = true;
                        cargo = rs.getString("nome_cargo");
                        idUsuario = rs.getInt("id_usuario");
                        nomeUsuario = rs.getString("nome_usuario") + " " + rs.getString("sobrenome_usuario");
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

    public static void criarTicket() {
        String[] opcoesServico = {"Compra", "Venda", "Consultoria", "Cancelar"};
        String[] periodosDisponiveis = {"Manhã", "Tarde", "Noite"};

        int resposta;

        int idVeiculo = 0;
        String tipoServico;
        String modeloVeiculo = "";
        String dataPreferida = "";
        String periodoPreferido = "";
        String descricaoProblema = "";

        //Campos do formulário
        JTextField campoIdVeiculo = new JTextField();
        JTextField campoModeloVeiculo = new JTextField();
        JTextField campoDataPreferida = new JTextField();
        JTextField campoDescricaoProblema = new JTextField();

        int respostaServico = JOptionPane.showOptionDialog(
                null,
                "Selecione o serviço desejado",
                "Serviços Velox",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesServico,
                opcoesServico[0]
        );

        // Se resposta for COMPRA
        if (respostaServico == 0) {
            tipoServico = "Compra";

            //Criação do formulário
            JPanel formularioServico = new JPanel(new GridLayout(3, 2, 10, 10));
            formularioServico.add(new JLabel("Id do veiculo: "));
            formularioServico.add(campoIdVeiculo);
            formularioServico.add(new JLabel("Data preferida para contato (DD/MM/YYYY): "));
            formularioServico.add(campoDataPreferida);
            formularioServico.add(new JLabel("Descrição do problema: "));
            formularioServico.add(campoDescricaoProblema);

            while (true) {
                resposta = JOptionPane.showConfirmDialog(
                        null,
                        formularioServico,
                        "Criar Ticket - Compra",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


                if (resposta == JOptionPane.OK_OPTION) {
                    String idString = campoIdVeiculo.getText();
                    dataPreferida = campoDataPreferida.getText();
                    descricaoProblema = campoDescricaoProblema.getText();

                    //Verificando se todos os campos estão preenchidos
                    if (idString.isEmpty() || dataPreferida.isEmpty() || descricaoProblema.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Todos os campos são obrigatórios!!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }

                    try (Connection conn = DriverManager.getConnection(URL,USER,PASS)){
                        idVeiculo = Integer.parseInt(idString);

                        String sql = "SELECT * FROM tb_estoque_veiculos WHERE id_veiculo = ?";

                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setInt(1, idVeiculo);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()){
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Veículo encontrado!!",
                                    "Sucesso!",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "O veículo não existe",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            continue;
                        }
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(
                                null,
                                "Erro ao conectar com o banco de dados",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                    //Escolha de período
                    int respostaPeriodo = JOptionPane.showOptionDialog(
                            null,
                            "Selecione o melhor período para entrarmos em contato!!",
                            "Escolha o período",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            periodosDisponiveis,
                            periodosDisponiveis[0]
                    );

                    if (respostaPeriodo == 0) {
                        periodoPreferido = "Manhã";
                    } else if (respostaPeriodo == 1) {
                        periodoPreferido = "Tarde";
                    } else if (respostaPeriodo == 2) {
                        periodoPreferido = "Noite";
                    }else {
                        continue;
                    }
                    break;

                } else {
                    break;
                }// Fim if else resposta = OK
            }// Fim do while
            if (resposta == JOptionPane.OK_OPTION) {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String selectModeloVeiculo = "SELECT modelo_veiculo FROM tb_estoque_veiculos WHERE id_veiculo = ?";

                    PreparedStatement psSelect = conn.prepareStatement(selectModeloVeiculo);

                    psSelect.setInt(1, idVeiculo);
                    ResultSet rs = psSelect.executeQuery();

                    if (rs.next()){
                        modeloVeiculo = rs.getString("modelo_veiculo");
                    }

                    String sql = "INSERT INTO tb_tickets (id_usuario, id_veiculo, tipo_servico, modelo_veiculo, data_preferida, periodo, descricao_problema, status_ticket)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, 'Aberto')";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setInt(1, idUsuario);
                    ps.setInt(2, idVeiculo);
                    ps.setString(3, tipoServico);
                    ps.setString(4, modeloVeiculo);
                    ps.setString(5, dataPreferida);
                    ps.setString(6, periodoPreferido);
                    ps.setString(7, descricaoProblema);

                    int linhasAfetadas = ps.executeUpdate();

                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ticket criado com sucesso!!",
                                "Sucesso!",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Erro ao criar ticket!",
                                "Erro!",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

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
            }

        //Se resposta for VENDA
        } else if (respostaServico == 1) {
            tipoServico = "Venda";

            //Criação do formulário
            JPanel formularioServico = new JPanel(new GridLayout(3, 2, 10, 10));
            formularioServico.add(new JLabel("Modelo do veiculo: "));
            formularioServico.add(campoModeloVeiculo);
            formularioServico.add(new JLabel("Data preferida para contato (DD/MM/YYYY): "));
            formularioServico.add(campoDataPreferida);
            formularioServico.add(new JLabel("Descrição da venda: "));
            formularioServico.add(campoDescricaoProblema);

            while (true) {
                resposta = JOptionPane.showConfirmDialog(
                        null,
                        formularioServico,
                        "Criar Ticket - Venda",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


                if (resposta == JOptionPane.OK_OPTION) {
                    modeloVeiculo = campoModeloVeiculo.getText();
                    dataPreferida = campoDataPreferida.getText();
                    descricaoProblema = campoDescricaoProblema.getText();

                    //Verificando se todos os campos estão preenchidos
                    if (modeloVeiculo.isEmpty() || dataPreferida.isEmpty() || descricaoProblema.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Todos os campos são obrigatórios!!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }

                    //Escolha de período
                    int respostaPeriodo = JOptionPane.showOptionDialog(
                            null,
                            "Selecione o melhor período para entrarmos em contato!!",
                            "Escolha o período",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            periodosDisponiveis,
                            periodosDisponiveis[0]
                    );

                    if (respostaPeriodo == 0) {
                        periodoPreferido = "Manhã";
                    } else if (respostaPeriodo == 1) {
                        periodoPreferido = "Tarde";
                    } else if (respostaPeriodo == 2) {
                        periodoPreferido = "Noite";
                    }else {
                        continue;
                    }
                    break;

                } else {
                    break;
                }// Fim if else resposta = OK
            }// Fim do while
            if (resposta == JOptionPane.OK_OPTION) {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "INSERT INTO tb_tickets (id_usuario, id_veiculo, tipo_servico, modelo_veiculo, data_preferida, periodo, descricao_problema, status_ticket)" +
                            "VALUES (?, null, ?, ?, ?, ?, ?, 'Aberto')";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setInt(1, idUsuario);
                    ps.setString(2, tipoServico);
                    ps.setString(3, modeloVeiculo);
                    ps.setString(4, dataPreferida);
                    ps.setString(5, periodoPreferido);
                    ps.setString(6, descricaoProblema);

                    int linhasAfetadas = ps.executeUpdate();

                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ticket criado com sucesso!!",
                                "Sucesso!",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Erro ao criar ticket!",
                                "Erro!",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

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
            }
        // Se resposta for CONSULTORIA
        }else if (respostaServico == 2){
            tipoServico = "Consultoria";

            //Criação do formulário
            JPanel formularioServico = new JPanel(new GridLayout(3, 2, 10, 10));
            formularioServico.add(new JLabel("Modelo do veiculo: "));
            formularioServico.add(campoModeloVeiculo);
            formularioServico.add(new JLabel("Data preferida para contato (DD/MM/YYYY): "));
            formularioServico.add(campoDataPreferida);
            formularioServico.add(new JLabel("Descrição da venda: "));
            formularioServico.add(campoDescricaoProblema);

            while (true) {
                resposta = JOptionPane.showConfirmDialog(
                        null,
                        formularioServico,
                        "Criar Ticket - Consultoria",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


                if (resposta == JOptionPane.OK_OPTION) {
                    modeloVeiculo = campoModeloVeiculo.getText();
                    dataPreferida = campoDataPreferida.getText();
                    descricaoProblema = campoDescricaoProblema.getText();

                    //Verificando se todos os campos estão preenchidos
                    if (modeloVeiculo.isEmpty() || dataPreferida.isEmpty() || descricaoProblema.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Todos os campos são obrigatórios!!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }

                    //Escolha de período
                    int respostaPeriodo = JOptionPane.showOptionDialog(
                            null,
                            "Selecione o melhor período para entrarmos em contato!!",
                            "Escolha o período",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            periodosDisponiveis,
                            periodosDisponiveis[0]
                    );

                    if (respostaPeriodo == 0) {
                        periodoPreferido = "Manhã";
                    } else if (respostaPeriodo == 1) {
                        periodoPreferido = "Tarde";
                    } else if (respostaPeriodo == 2) {
                        periodoPreferido = "Noite";
                    }else {
                        continue;
                    }
                    break;

                } else {
                    break;
                }// Fim if else resposta = OK
            }// Fim do while
            if (resposta == JOptionPane.OK_OPTION) {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "INSERT INTO tb_tickets (id_usuario, id_veiculo, tipo_servico, modelo_veiculo, data_preferida, periodo, descricao_problema, status_ticket)" +
                            "VALUES (?, null, ?, ?, ?, ?, ?, 'Aberto')";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setInt(1, idUsuario);
                    ps.setString(2, tipoServico);
                    ps.setString(3, modeloVeiculo);
                    ps.setString(4, dataPreferida);
                    ps.setString(5, periodoPreferido);
                    ps.setString(6, descricaoProblema);

                    int linhasAfetadas = ps.executeUpdate();

                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Ticket criado com sucesso!!",
                                "Sucesso!",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Erro ao criar ticket!",
                                "Erro!",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

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
            }
        }else if (respostaServico == 3) {
            JOptionPane.showMessageDialog(null, "Cancelando...");
        }
    }//Fim do método criarTicket
}//Fim da classe velox
