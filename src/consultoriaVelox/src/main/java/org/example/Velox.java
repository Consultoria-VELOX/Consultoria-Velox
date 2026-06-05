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
                        "Seja bem vindo ao Velox! Escolha uma opção",
                        "Cadastro/Login",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]
                );

                if (respostaRegistroLogin == 0) {
                    Usuario.cadastrarUsuario();
                } else if (respostaRegistroLogin == 1) {
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
                        Estoque.verEstoque();
                    }else if (respostaCliente == 4) {
                        JOptionPane.showMessageDialog(null, "Volte logo...");
                        Sessao.logado = false;
                        Sessao.idUsuario = 0;
                        Sessao.nomeUsuario = "";
                    }//Fim if else escolhas
                }else if (Sessao.cargo.equals("Gestor")){
                    String[] opcoesGestor = {"Visualizar Tickets", "Atualizar status dos Tickets", "Ver Estoque", "Adicionar veículos ao Estoque", "Atualizar veículo do estoque", "Excluir veículos do estoque", "Sair da conta"};

                    int respostaGestor = JOptionPane.showOptionDialog(
                            null,
                            "Olá " + Sessao.nomeUsuario + "! Escolha uma opção",
                            "Opções",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoesGestor,
                            opcoesGestor[0]
                    );

                    if(respostaGestor == 0){ //Visualizar Tickets
                        Ticket.verTickets();
                    }else if(respostaGestor == 1){ //Atualizar status de tickets
                        Ticket.atualizarStatusTicket();
                    }else if(respostaGestor == 2){ //Ver estoque
                        Estoque.verEstoque();
                    }else if(respostaGestor == 3){ //Adicionar veículo ao estoque
                        Estoque.adicionarVeiculo();
                    }else if(respostaGestor == 4){ //Atualizar veículo do estoque
                        Estoque.atualizarVeiculo();
                    }else if(respostaGestor == 5){ //"Deletar" veículo do estoque
                        Estoque.desativarVeiculo();
                    }else if(respostaGestor == 6){ //Sair da conta
                        JOptionPane.showMessageDialog(null, "Volte logo...");
                        Sessao.logado = false;
                        Sessao.idUsuario = 0;
                        Sessao.nomeUsuario = "";
                    }
                }else if (Sessao.cargo.equals("Administrador")){
                    String[] opcoesAdmin = {"Alterar cargo de usuário", "Visualizar Usuários", "Visualizar Tickets", "Atualizar status dos Tickets", "Ver Estoque", "Adicionar veículos ao Estoque", "Atualizar veículo do estoque", "Excluir veículos do estoque", "Sair da conta"};

                    int respostaAdmin = JOptionPane.showOptionDialog(
                            null,
                            "Olá " + Sessao.nomeUsuario + "! Escolha uma opção",
                            "Opções",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoesAdmin,
                            opcoesAdmin[0]
                    );

                    if (respostaAdmin == 0) { //Mudar cargo de usuário
                        Usuario.mudarCargo();
                    }else if(respostaAdmin == 1){ //Visuaizar Usuários
                        Usuario.listarUsuarios();
                    }else if (respostaAdmin == 2) { //Visualizar Tickets
                        Ticket.verTickets();
                    } else if (respostaAdmin == 3) { //Atualizar status de tickets
                        Ticket.atualizarStatusTicket();
                    } else if (respostaAdmin == 4) { //Ver estoque
                        Estoque.verEstoque();
                    } else if (respostaAdmin == 5) { //Adicionar veículo ao estoque
                        Estoque.adicionarVeiculo();
                    } else if (respostaAdmin == 6) { //Atualizar veículo do estoque
                        Estoque.atualizarVeiculo();
                    } else if (respostaAdmin == 7) { //"Deletar" veículo do estoque
                        Estoque.desativarVeiculo();
                    } else if (respostaAdmin == 8) { //Sair da conta
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
