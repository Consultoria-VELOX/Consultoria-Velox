package org.example;

import javax.swing.*;
import java.sql.*;
import java.awt.*;

public class Estoque {
    public static void verEstoque(){
        String estoqueVeiculos = "";
        try (Connection conn = Conexao.conectar()){
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
            }

            // Volta o cursor para antes do primeiro registro
            rs.beforeFirst();

            while(rs.next()){
                int idVeiculo = rs.getInt("id_veiculo");
                String marcaVeiculo = rs.getString("marca_veiculo");
                String modeloVeiculo = rs.getString("modelo_veiculo");
                int ano = rs.getInt("ano");
                double preco = rs.getDouble("preco");
                String descricao = rs.getString("descricao");
                int disponivelBool =  rs.getInt("disponivel");
                String disponivel = "";

                if(disponivelBool == 1){
                    disponivel = "Sim";
                }else{
                    disponivel = "Não";
                }

                estoqueVeiculos += "ID: "+idVeiculo+
                        "\nMarca: "+marcaVeiculo+
                        "\nModelo: "+modeloVeiculo+
                        "\nAno: "+ano+
                        "\nPreço: R$"+preco+
                        "\nDescrição: "+descricao+
                        "\nDisponivel: "+disponivel+"\n\n";
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
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: "+e.getMessage());
        }
    }
}
