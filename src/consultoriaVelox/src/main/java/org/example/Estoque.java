package org.example;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class Estoque {
    public static void verEstoque() {
        while (true) {
            String estoqueVeiculos = "";
            try (Connection conn = Conexao.conectar()) {
                String sql = "SELECT * FROM tb_estoque_veiculos WHERE disponivel = 1";

                PreparedStatement ps = conn.prepareStatement(sql,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Nenhum veículo encontrado!",
                            "Estoque de veículos",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    break;
                }

                // Volta o cursor para antes do primeiro registro
                rs.beforeFirst();

                while (rs.next()) {
                    int idVeiculo = rs.getInt("id_veiculo");
                    String marcaVeiculo = rs.getString("marca_veiculo");
                    String modeloVeiculo = rs.getString("modelo_veiculo");
                    int ano = rs.getInt("ano");
                    double preco = rs.getDouble("preco");
                    String descricao = rs.getString("descricao");
                    int disponivelBool = rs.getInt("disponivel");
                    String disponivel = "";

                    if (disponivelBool == 1) {
                        disponivel = "Sim";
                    } else {
                        disponivel = "Não";
                    }

                    estoqueVeiculos += "ID: " + idVeiculo +
                            "\nMarca: " + marcaVeiculo +
                            "\nModelo: " + modeloVeiculo +
                            "\nAno: " + ano +
                            "\nPreço: R$" + preco +
                            "\nDescrição: " + descricao +
                            "\nDisponivel: " + disponivel + "\n\n";
                }

                // JTextArea para exibir o texto
                JTextArea textArea = new JTextArea(estoqueVeiculos);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                // JScrollPane envolve o textArea e adiciona o scroll
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 600));

                JOptionPane.showMessageDialog(
                        null,
                        scrollPane,
                        "Estoque de Veículos",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            }
            break;
        }//Fim loop while
    }//Fim do método verEstoque()

    public static void adicionarVeiculo() {
        JTextField marcaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField anoField = new JTextField();
        JTextField placaField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField descricaoField = new JTextField();

        String marca = "";
        String modelo = "";
        int ano = 0;
        String placa = "";
        double preco = 0.0;
        String descricao = "";

        JPanel formularioVeiculo = new JPanel(new GridLayout(6, 2, 10, 10));
        formularioVeiculo.add(new JLabel("Marca:"));
        formularioVeiculo.add(marcaField);
        formularioVeiculo.add(new JLabel("Modelo:"));
        formularioVeiculo.add(modeloField);
        formularioVeiculo.add(new JLabel("Ano:"));
        formularioVeiculo.add(anoField);
        formularioVeiculo.add(new JLabel("Placa:"));
        formularioVeiculo.add(placaField);
        formularioVeiculo.add(new JLabel("Preço:"));
        formularioVeiculo.add(precoField);
        formularioVeiculo.add(new JLabel("Descrição:"));
        formularioVeiculo.add(descricaoField);

        loopPrincipal:
        while (true) {
            while (true) {
                int respostaFormulario = JOptionPane.showConfirmDialog(
                        null,
                        formularioVeiculo,
                        "Adicionar veículo",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (respostaFormulario == JOptionPane.OK_OPTION) {
                    marca = marcaField.getText();
                    modelo = modeloField.getText();
                    ano = Integer.parseInt(anoField.getText());
                    placa = placaField.getText();
                    preco = Double.parseDouble(precoField.getText());
                    descricao = descricaoField.getText();

                    if (marca.isEmpty() || modelo.isEmpty() || placa.isEmpty() || descricao.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                        continue;
                    } else if (ano < 1970 || ano > 2026) {
                        JOptionPane.showMessageDialog(null, "Por favor, insira um ano válido");
                        continue;
                    } else if (preco <= 0.0) {
                        JOptionPane.showMessageDialog(null, "Por favor, insira preço válido");
                        continue;
                    }
                    break;

                } else {
                    JOptionPane.showMessageDialog(null, "Cancelando...");
                    break loopPrincipal;
                }//Fim id else respostaFormulario
            }//Fim loop while de verificação de dados

            try (Connection conn = Conexao.conectar()) {
                String sql = "INSERT INTO tb_estoque_veiculos (marca_veiculo, modelo_veiculo, ano, placa_veiculo, preco, descricao, disponivel) VALUES (?, ?, ?, ?, ?, ?, 1)";

                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, marca);
                ps.setString(2, modelo);
                ps.setInt(3, ano);
                ps.setString(4, placa);
                ps.setDouble(5, preco);
                ps.setString(6, descricao);

                int linhasAfetadas = ps.executeUpdate();
                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Veículo adicionado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar veículo. Tente novamente.");
                    continue;
                }
                break;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
            }//Fim try catch
        }//Fim loop while
    }//Fim do método adicionarVeiculo

    public static void atualizarVeiculo(){
        String[] opcoes = {"Atualizar informações", "Cancelar"};

        JTextField marcaField = new JTextField();
        JTextField modeloField = new JTextField();
        JTextField anoField = new JTextField();
        JTextField placaField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField descricaoField = new JTextField();

        int idVeiculo = 0;
        String marca = "";
        String marcaNova = "";
        String modelo = "";
        String modeloNovo = "";
        int ano = 0;
        int anoNovo = 0;
        String placa = "";
        String placaNova = "";
        double preco = 0.0;
        double precoNovo = 0.0;
        String descricao = "";
        String descricaoNova = "";

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
                String idString = JOptionPane.showInputDialog(null, "Insira o id do veículo", "Atualizar veículo", JOptionPane.QUESTION_MESSAGE);

                if (idString == null || idString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Cancelando...");
                    break loopPrincipal;
                } else {
                    idVeiculo = Integer.parseInt(idString);
                    try (Connection conn = Conexao.conectar()) {
                        String sql = "SELECT * FROM tb_tickets WHERE id_veiculo = ? AND disponivel = 1";

                        PreparedStatement ps = conn.prepareStatement(sql);

                        ps.setInt(1, idVeiculo);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            JOptionPane.showMessageDialog(null, "Veículo encontrado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                            marca = rs.getString("marca_veiculo");
                            modelo = rs.getString("modelo_veiculo");
                            ano = rs.getInt("ano_veiculo");
                            placa = rs.getString("placa_veiculo");
                            preco = rs.getDouble("preco_veiculo");
                            descricao = rs.getString("descricao_veiculo");

                        } else {
                            JOptionPane.showMessageDialog(null, "Veículo não existe, tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        break;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }//Fim try catch

                    JPanel formularioVeiculo = new JPanel(new GridLayout(6, 2, 10, 10));
                    formularioVeiculo.add(new JLabel("Marca ("+marca+"):"));
                    formularioVeiculo.add(marcaField);
                    formularioVeiculo.add(new JLabel("Modelo("+modelo+"):"));
                    formularioVeiculo.add(modeloField);
                    formularioVeiculo.add(new JLabel("Ano("+ano+"):"));
                    formularioVeiculo.add(anoField);
                    formularioVeiculo.add(new JLabel("Placa("+placa+"):"));
                    formularioVeiculo.add(placaField);
                    formularioVeiculo.add(new JLabel("Preço("+preco+"):"));
                    formularioVeiculo.add(precoField);
                    formularioVeiculo.add(new JLabel("Descrição("+descricao+"):"));
                    formularioVeiculo.add(descricaoField);

                    while (true) {
                        int respostaFormulario = JOptionPane.showConfirmDialog(
                                null,
                                formularioVeiculo,
                                "Atualizar Veículo",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                        if (respostaFormulario == JOptionPane.OK_OPTION) {
                            marcaNova = marcaField.getText();
                            modeloNovo = modeloField.getText();
                            anoNovo = Integer.parseInt(anoField.getText());
                            placaNova = placaField.getText();
                            precoNovo = Double.parseDouble(precoField.getText());
                            descricaoNova = descricaoField.getText();

                            if (dataPreferidaNova.isEmpty() || descricaoProblemaNovo.isEmpty()) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Todos os campos são obrigatórios!!",
                                        "Erro",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                continue;
                            }
                            break;
                        }else {
                            JOptionPane.showMessageDialog(null, "Cancelando...");
                            break loopPrincipal;
                        }
                    }//Fim do while
                }
            }// Fim loop while de verificação de id
        }//Fim loopPrincipal
    }
}//Fim da classe Estoque
