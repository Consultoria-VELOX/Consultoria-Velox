package org.example;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class Ticket {
    public static void criarTicket() {
        String[] opcoesServico = {"Compra", "Venda", "Consultoria", "Cancelar"};
        String[] periodosDisponiveis = {"Manhã", "Tarde", "Noite"};

        int respostaFormulario;

        int idVeiculo = 0;
        String idString = "";
        String tipoServico = "";
        String modeloVeiculo = "";
        String dataPreferida = "";
        String periodoPreferido = "";
        String descricaoProblema = "";

        //Campos do formulário
        JTextField campoIdVeiculo = new JTextField();
        JTextField campoModeloVeiculo = new JTextField();
        JTextField campoDataPreferida = new JTextField();
        JTextField campoDescricaoProblema = new JTextField();

        loopPrincipal:
        while (true) {
            // Usuário escolhe o serviço
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

            //Criação do formulário
            JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
            if (respostaServico == 0) {
                tipoServico = "Compra";
                formulario.add(new JLabel("ID do veículo:"));
                formulario.add(campoIdVeiculo);
            } else if (respostaServico == 1) {
                tipoServico = "Venda";
                formulario.add(new JLabel("Modelo do veículo:"));
                formulario.add(campoModeloVeiculo);
            } else if (respostaServico == 2) {
                tipoServico = "Consultoria";
                formulario.add(new JLabel("Modelo do veículo:"));
                formulario.add(campoModeloVeiculo);
            } else {
                JOptionPane.showMessageDialog(null, "Cancelando...");
                break loopPrincipal;
            }
            formulario.add(new JLabel("Data preferida para contato (DD/MM/YYYY): "));
            formulario.add(campoDataPreferida);
            formulario.add(new JLabel("Descrição do problema: "));
            formulario.add(campoDescricaoProblema);


            // Se repetirá até usuário preencher os campos corretamente
            while (true) {
                respostaFormulario = JOptionPane.showConfirmDialog(
                        null,
                        formulario,
                        "Criar Ticket - " + tipoServico,
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (respostaFormulario == JOptionPane.OK_OPTION) {
                    dataPreferida = campoDataPreferida.getText();
                    descricaoProblema = campoDescricaoProblema.getText();
                    boolean verificacao = false;

                /*
                    Verificando se todos os campos foram preenchidos de acordo
                    com a escolha do cliente, caso contrário, o formulário se repetirá até que sejam preenchidos
                */
                    if (tipoServico.equals("Compra")) {
                        idString = campoIdVeiculo.getText();
                        verificacao = idString.isEmpty();
                    } else if (tipoServico.equals("Venda") || tipoServico.equals("Consultoria")) {
                        modeloVeiculo = campoModeloVeiculo.getText();
                        verificacao = modeloVeiculo.isEmpty();
                    }

                    if (verificacao || dataPreferida.isEmpty() || descricaoProblema.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Todos os campos são obrigatórios!!",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }

                    //Verificando se o id informado realmente existe e armazenando o modelo do veículo em uma variável
                    //Apenas se o serviço escolhido for COMPRA
                    if (tipoServico.equals("Compra")) {
                        try (Connection conn = Conexao.conectar()) {
                            idVeiculo = Integer.parseInt(idString);

                            String sql = "SELECT modelo_veiculo FROM tb_estoque_veiculos WHERE id_veiculo = ? AND disponivel = 1";

                            PreparedStatement ps = conn.prepareStatement(sql);

                            ps.setInt(1, idVeiculo);
                            ResultSet rs = ps.executeQuery();

                            //Se alguma linha retornar
                            if (rs.next()) {
                                modeloVeiculo = rs.getString("modelo_veiculo");
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Veículo encontrado!!",
                                        "Sucesso!",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "O veículo não existe ou está indisponível!",
                                        "Erro",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                continue;
                            }// Fim if else
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Erro ao conectar com o banco de dados",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }//Fim try catch
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
                    } else {
                        continue;
                    }//Fim if else
                    break;
                } else {
                    //Se opção cancelar for escolhida o looping quebra
                    JOptionPane.showMessageDialog(null, "Cancelando...", "Velox", JOptionPane.INFORMATION_MESSAGE);
                    break loopPrincipal;
                }// Fim if else
            }// Fim loop while

            //Depois de passar por todoas as verificações
            if (respostaFormulario == JOptionPane.OK_OPTION) {
                try (Connection conn = Conexao.conectar()) {
                    String sql = "INSERT INTO tb_tickets (id_usuario, id_veiculo, tipo_servico, modelo_veiculo, data_preferida, periodo, descricao_problema, status_ticket)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, 'Aberto')";

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, Sessao.idUsuario);

                    //Inserindo id do veiculo de acordo com o serviço escolhido
                    if (tipoServico.equals("Compra")) {
                        ps.setInt(2, idVeiculo);
                    } else {
                        ps.setNull(2, Types.INTEGER);
                    }

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
                break loopPrincipal;
            }

        }
    }// Fim do método criarTicket()

    public static void verTickets() {
        String verTickets = "";
        String sql;
        loopPrincipal:
        while (true) {
            try (Connection conn = Conexao.conectar()) {
                if (Sessao.cargo.equals("Cliente")) {
                    sql = "SELECT t.id_ticket, t.id_usuario, t.id_veiculo, u.nome_usuario, u.sobrenome_usuario, u.email, u.telefone, t.tipo_servico, t.modelo_veiculo, t.data_preferida, t.periodo, t.descricao_problema, t.status_ticket  " +
                            "FROM tb_tickets t " +
                            "JOIN tb_usuarios u ON t.id_usuario = u.id_usuario " +
                            "WHERE t.id_usuario = ? AND t.status_ticket <> 'Resolvido' AND t.status_ticket <> 'Cancelado'";
                } else {
                    sql = "SELECT t.id_ticket, t.id_usuario, t.id_veiculo, u.nome_usuario, u.sobrenome_usuario, u.email, u.telefone, t.tipo_servico, t.modelo_veiculo, t.data_preferida, t.periodo, t.descricao_problema, t.status_ticket  " +
                            "FROM tb_tickets t " +
                            "JOIN tb_usuarios u ON t.id_usuario = u.id_usuario";
                }

                PreparedStatement ps = conn.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                if (Sessao.cargo.equals("Cliente")) {
                    ps.setInt(1, Sessao.idUsuario);
                }


                ResultSet rs = ps.executeQuery();


                if (!rs.next()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Nenhum ticket encontrado!",
                            "Tickets",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    break loopPrincipal;
                }

                // Volta o cursor para antes do primeiro registro
                rs.beforeFirst();

                while (rs.next()) {
                    int idTicket = rs.getInt("id_ticket");
                    int idUsuario = rs.getInt("id_usuario");
                    int idVeiculo = rs.getInt("id_veiculo");
                    String nomeUsuario = rs.getString("nome_usuario") + " " + rs.getString("sobrenome_usuario");
                    String emailUsuario = rs.getString("email");
                    String telefoneUsuario = rs.getString("telefone");
                    String tipoServico = rs.getString("tipo_servico");
                    String modeloVeiculo = rs.getString("modelo_veiculo");
                    String dataPreferida = rs.getString("data_preferida");
                    String periodo = rs.getString("periodo");
                    String descricaoProblema = rs.getString("descricao_problema");
                    String statusTicket = rs.getString("status_ticket");

                    verTickets += "ID Ticket: " + idTicket + "\n" +
                            "Id do Usuário: " + idUsuario + "\n" +
                            "Nome do Usuário: " + nomeUsuario + "\n" +
                            "Email do Usuário: " + emailUsuario + "\n" +
                            "Telefone do Usuário: " + telefoneUsuario + "\n" +
                            "Tipo de serviço: " + tipoServico + "\n" +
                            "Id do veículo: " + idVeiculo + "\n" +
                            "Modelo do veículo: " + modeloVeiculo + "\n" +
                            "Data preferida: " + dataPreferida + "\n" +
                            "Período: " + periodo + "\n" +
                            "Descrição do problema: " + descricaoProblema + "\n" +
                            "Status do ticket: " + statusTicket + "\n\n";
                }//Fim while

                // JTextArea para exibir o texto
                JTextArea textArea = new JTextArea(verTickets);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                // JScrollPane envolve o textArea e adiciona o scroll
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 600));

                JOptionPane.showMessageDialog(
                        null,
                        scrollPane,
                        "Tickets",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Erro ao conectar ao banco de dados: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }// Fim try catch
            break;
        }
    }//Fim método verTickets();

    public static void atualizarTicket() {
        String[] opcoes = {"Atualizar informações", "Cancelar ticket", "Cancelar"};
        String[] periodosDisponiveis = {"Manhã", "Tarde", "Noite"};

        int idTicket = 0;
        int idVeiculo = 0;
        String tipoServico = "";
        String modeloVeiculo = "";
        String dataPreferidaAtual = "";
        String dataPreferidaNova = "";
        String periodoPreferidoAtual = "";
        String periodoPreferidoNovo = "";
        String descricaoProblemaAtual = "";
        String descricaoProblemaNovo = "";

        loopPrincipal:
        while (true) {
            int resposta = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção",
                    "Atualizar ticket",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            //Loop até o usuário inserir um id válido
            if (resposta == 2) {
                JOptionPane.showMessageDialog(null, "Cancelando...");
                break;
            }
            while (true) {
                String idString = JOptionPane.showInputDialog(null, "Insira o id do seu ticket", "Atualizar ticket", JOptionPane.QUESTION_MESSAGE);

                if (idString == null || idString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cancelando...");
                    break loopPrincipal;
                } else {
                    idTicket = Integer.parseInt(idString);
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "SELECT * FROM tb_tickets WHERE id_ticket = ? AND id_usuario = ? AND status_ticket != 'Cancelado'";

                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setInt(1, idTicket);
                        ps.setInt(2, Sessao.idUsuario);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "Ticket encontrado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                            tipoServico = rs.getString("tipo_servico");
                            modeloVeiculo = rs.getString("modelo_veiculo");
                            dataPreferidaAtual = rs.getString("data_preferida");
                            periodoPreferidoAtual = rs.getString("periodo");
                            descricaoProblemaAtual = rs.getString("descricao_problema");

                        } else {
                            JOptionPane.showMessageDialog(null, "Ticket não existe, tente novamente.", "Ticket não encontrado", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        break;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }//Fim try catch
                }
            }// Fim loop while

            if (resposta == 0) {
                //Campos do formulário
                JTextField campoDataPreferida = new JTextField();
                JTextField campoDescricaoProblema = new JTextField();

                //Criação do formulário
                JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));

                formulario.add(new JLabel("Data preferida (" + dataPreferidaAtual + "): "));
                formulario.add(campoDataPreferida);
                formulario.add(new JLabel("Descrição do problema (" + descricaoProblemaAtual + "):"));
                formulario.add(campoDescricaoProblema);

                while (true) {
                    int respostaFormulario = JOptionPane.showConfirmDialog(
                            null,
                            formulario,
                            "Atualizar Ticket - " + tipoServico,
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (respostaFormulario == JOptionPane.OK_OPTION) {
                        dataPreferidaNova = campoDataPreferida.getText();
                        descricaoProblemaNovo = campoDescricaoProblema.getText();

                        if (dataPreferidaNova.isEmpty()) {
                            dataPreferidaNova = dataPreferidaAtual;
                        }
                        if (descricaoProblemaNovo.isEmpty()) {
                            descricaoProblemaNovo = descricaoProblemaAtual;
                        }
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancelando...");
                        break loopPrincipal;
                    }
                }//Fim do while

                //Escolha de período
                int respostaPeriodo = JOptionPane.showOptionDialog(
                        null,
                        "Selecione o melhor período para entrarmos em contato!! - Período preferido atual: " + periodoPreferidoAtual,
                        "Escolha o período",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        periodosDisponiveis,
                        periodosDisponiveis[0]
                );

                if (respostaPeriodo == 0) {
                    periodoPreferidoNovo = "Manhã";
                } else if (respostaPeriodo == 1) {
                    periodoPreferidoNovo = "Tarde";
                } else if (respostaPeriodo == 2) {
                    periodoPreferidoNovo = "Noite";
                }

                try (Connection conn = Conexao.conectar()) {
                    String sql = "UPDATE tb_tickets SET data_preferida = ?, periodo = ?, descricao_problema = ? WHERE id_ticket = ? AND id_usuario = ?";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(1, dataPreferidaNova);
                    ps.setString(2, periodoPreferidoNovo);
                    ps.setString(3, descricaoProblemaNovo);
                    ps.setInt(4, idTicket);
                    ps.setInt(5, Sessao.idUsuario);

                    int linhasAlteradas = ps.executeUpdate();

                    if (linhasAlteradas > 0) {
                        JOptionPane.showMessageDialog(null, "Ticket atualizado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar ticket", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }//Fim do try catch
            } else if (resposta == 1) {
                int confirmarOpcao = JOptionPane.showConfirmDialog(
                        null,
                        "Tem certeza que deseja cancelar seu ticket?",
                        "Cancelar Ticket",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirmarOpcao == 0) {
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "UPDATE tb_tickets SET status_ticket = 'Cancelado' WHERE id_ticket = ?";

                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, idTicket);

                        int linhasAlteradas = ps.executeUpdate();

                        if (linhasAlteradas > 0) {
                            JOptionPane.showMessageDialog(null, "Ticket cancelado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao cancelar ticket", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }// Fim if resposta = Atualizar tickets
        }
    }//Fim do método atualizarTicket();

    public static void atualizarStatusTicket() {
        String idString = "";
        int idTicket = 0;

        loopPrincipal:
        while (true) {
            loopIdTicket:
            while (true) {
                idString = JOptionPane.showInputDialog(null, "Insira o id do seu ticket", "Atualizar status do ticket", JOptionPane.QUESTION_MESSAGE);
                if (idString != null) {
                    try (Connection conn = Conexao.conectar()) {
                        idTicket = Integer.parseInt(idString);
                        String sql = "SELECT * FROM tb_tickets WHERE id_ticket = ?";

                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setInt(1, idTicket);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "Ticket Encontrado!", "Sucesso!!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "O ticket não existe!", "Erro", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        break;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }//Fim try catch
                } else {
                    JOptionPane.showMessageDialog(null, "Cancelando...");
                    break loopPrincipal;
                }//Fim if else botão cancelar

            }//Fim loopIdTicket

            try (Connection conn = Conexao.conectar()) {
                String[] opcoesStatus = {"Aberto", "Em andamento", "Resolvido", "Cancelar Ticket", "Cancelar"};
                String novoStatus = "";

                int opcaoSelecionada = JOptionPane.showOptionDialog(
                        null,
                        "Selecione o novo status do ticket",
                        "Atualizar Status",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoesStatus,
                        opcoesStatus[0]
                );

                if (opcaoSelecionada == 0) {
                    novoStatus = "Aberto";
                } else if (opcaoSelecionada == 1) {
                    novoStatus = "Em Andamento";
                } else if (opcaoSelecionada == 2) {
                    novoStatus = "Resolvido";
                } else if (opcaoSelecionada == 3) {
                    novoStatus = "Cancelado";
                } else {
                    JOptionPane.showMessageDialog(null, "Encerrando...");
                    break;
                }

                int confirmarOpcao = JOptionPane.showConfirmDialog(
                        null,
                        "Tem certeza que deseja alterar o status do ticket?",
                        "Atualizar Status",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (confirmarOpcao == JOptionPane.OK_OPTION) {
                    String sql = "UPDATE tb_tickets SET status_ticket = ? WHERE id_ticket = ?";

                    PreparedStatement ps = conn.prepareStatement(sql);

                    ps.setString(1, novoStatus);
                    ps.setInt(2, idTicket);

                    int linhasAlteradas = ps.executeUpdate();

                    if (linhasAlteradas > 0) {
                        JOptionPane.showMessageDialog(null, "Status do ticket atualizado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao atualizar ticket", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cancelando...");
                    break;
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }//Fim do loopPrincipal
    }//Fim do método atualizarStatusTicket()
}//Fim da classe Ticket
