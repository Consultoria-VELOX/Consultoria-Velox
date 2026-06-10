package org.example;

import javax.swing.*;

public class Velox {

    public static void main(String[] args) {

        loopPrincipal:
        while (true) {
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
                    String[] opcoesCliente = {"Criar Ticket", "Meus Tickets", "Atualizar ticket", "Ver Estoque", "Sair da conta"};

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
                    } else if (respostaCliente == 3) {
                        Estoque.verEstoque();
                    } else if (respostaCliente == 4) {
                        JOptionPane.showMessageDialog(null, "Volte logo...");
                        Sessao.logado = false;
                        Sessao.idUsuario = 0;
                        Sessao.nomeUsuario = "";
                    }//Fim if else escolhas
                } else if (Sessao.cargo.equals("Gestor")) {
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

                    if (respostaGestor == 0) { //Visualizar Tickets
                        Ticket.verTickets();
                    } else if (respostaGestor == 1) { //Atualizar status de tickets
                        Ticket.atualizarStatusTicket();
                    } else if (respostaGestor == 2) { //Ver estoque
                        Estoque.verEstoque();
                    } else if (respostaGestor == 3) { //Adicionar veículo ao estoque
                        Estoque.adicionarVeiculo();
                    } else if (respostaGestor == 4) { //Atualizar veículo do estoque
                        Estoque.atualizarVeiculo();
                    } else if (respostaGestor == 5) { //"Deletar" veículo do estoque
                        Estoque.desativarVeiculo();
                    } else if (respostaGestor == 6) { //Sair da conta
                        JOptionPane.showMessageDialog(null, "Volte logo...");
                        Sessao.logado = false;
                        Sessao.idUsuario = 0;
                        Sessao.nomeUsuario = "";
                    }
                } else if (Sessao.cargo.equals("Administrador")) {
                    String[] opcoesCategoria = {"Opções de Usuários", "Opções de Tickets", "Opções de Estoque", "Sair da conta"};
                    //String[] opcoesAdmin;
                    //= {"Alterar cargo de usuário", "Visualizar Usuários", "Visualizar Tickets", "Atualizar status dos Tickets", "Ver Estoque", "Adicionar veículos ao Estoque", "Atualizar veículo do estoque", "Excluir veículos do estoque", "Sair da conta"};

                    while (true) {

                        int respostaCategoria = JOptionPane.showOptionDialog(
                                null,
                                "Olá " + Sessao.nomeUsuario + "! Escolha uma opção",
                                "Opções",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                opcoesCategoria,
                                opcoesCategoria[0]
                        );

                        if (respostaCategoria == 0) {
                            String[] opcoesAdmin = {"Listar Usuários", "Alterar cargo de usuário", "Voltar"};

                            loopUsuario:
                            while (true) {
                                int respostaAdmin = JOptionPane.showOptionDialog(
                                        null,
                                        "Escolha uma opção",
                                        "Opções",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        opcoesAdmin,
                                        opcoesAdmin[0]
                                );

                                switch (respostaAdmin) {
                                    case 0 ->
                                        Usuario.listarUsuarios();
                                    case 1 ->
                                        Usuario.mudarCargo();
                                    default -> {
                                        break loopUsuario;
                                    }
                                }
                            }//Fim loopUsuario
                        } else if (respostaCategoria == 1) {
                            String[] opcoesAdmin = {"Visualizar Tickets", "Atualizar status dos Tickets", "Voltar"};

                            loopTickets:
                            while (true) {
                                int respostaAdmin = JOptionPane.showOptionDialog(
                                        null,
                                        "Escolha uma opção",
                                        "Opções",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        opcoesAdmin,
                                        opcoesAdmin[0]
                                );

                                switch (respostaAdmin) {
                                    case 0 ->
                                        Ticket.verTickets();
                                    case 1 ->
                                        Ticket.atualizarStatusTicket();
                                    default -> {
                                        break loopTickets;
                                    }
                                }
                            }//Fim loopTickets
                        } else if (respostaCategoria == 2) {
                            String[] opcoesAdmin = {"Ver Estoque", "Adicionar veículos ao Estoque", "Atualizar veículo do estoque", "Excluir veículos do estoque", "Voltar"};

                            loopEstoque:
                            while (true) {
                                int respostaAdmin = JOptionPane.showOptionDialog(
                                        null,
                                        "Escolha uma opção",
                                        "Opções",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        opcoesAdmin,
                                        opcoesAdmin[0]
                                );

                                switch (respostaAdmin) {
                                    case 0 ->
                                        Estoque.verEstoque();
                                    case 1 ->
                                        Estoque.adicionarVeiculo();
                                    case 2 ->
                                        Estoque.atualizarVeiculo();
                                    case 3 ->
                                        Estoque.desativarVeiculo();
                                    default -> {
                                        break loopEstoque;
                                    }
                                }
                            }//Fim loopEstoque
                        } else if (respostaCategoria == 3) {
                            JOptionPane.showMessageDialog(null, "Volte logo...");
                            Sessao.logado = false;
                            Sessao.idUsuario = 0;
                            Sessao.nomeUsuario = "";
                            break;
                        }///Fim if else de respostaCategoria
                    }//Fim do loop de escolha de categoria
                }//Fim if else de opções por cargo
            }//Fim do loop de logado
        }//Fim do loopPrincipal
    }// Fim do método mai
}//Fim da classe velox
