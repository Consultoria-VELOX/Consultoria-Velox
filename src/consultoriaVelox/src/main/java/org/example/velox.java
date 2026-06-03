package org.example;

import javax.swing.*;

public class Velox {
    public static void main(String[] args) {

        loopPrincipal: while(true) {
            //Cadastro e login
            while (Sessao.logado == false) {
                String[] opcoes = {"Cadastrar-se", "Logar-se", "Sair"};
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
                    Usuario.cadastrarUsuario();
                } else if (respostaRegistroLogin == 1) {
                    JOptionPane.showMessageDialog(null, "Login selecionado", "Velox", JOptionPane.INFORMATION_MESSAGE);
                    Usuario.loginUsuario();
                } else if (respostaRegistroLogin == 2) {
                    JOptionPane.showMessageDialog(null, "Encerrando...", "Velox", JOptionPane.INFORMATION_MESSAGE);
                    break loopPrincipal;
                }//Fim if else de login
            }// Fim do while logado


            while (Sessao.logado == true) {
                if (Sessao.cargo.equals("Cliente")) {
                    String[] opcoesCliente = {"Criar Ticket", "Meus Tickets", "Atualizar ticket","Ver Estoque", "Sair da conta"};

                    int respostaCliente = JOptionPane.showOptionDialog(
                            null,
                            "Olá " + Sessao.nomeUsuario + "! Escolha uma opção",
                            "Opções",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoesCliente,
                            opcoesCliente[0]
                    );

                    if (respostaCliente == 0) {
                        Ticket.criarTicket();
                    } else if (respostaCliente == 1) {
                        Ticket.verTickets();
                    } else if (respostaCliente == 2) {
                        Ticket.atualizarTicket();
                    }else if (respostaCliente == 3) {
                        System.out.println("Ver estoque");
                    }else if (respostaCliente == 4) {
                        JOptionPane.showMessageDialog(null, "Volte logo...");
                        Sessao.logado = false;
                        Sessao.idUsuario = 0;
                        Sessao.nomeUsuario = "";
                    }
                }
            }
        }

    }// Fim do método main

}//Fim da classe velox
