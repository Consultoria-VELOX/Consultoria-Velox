let bancoDeTickets = [
    { id: "1024", assunto: "Consultoria de Compra - Honda Civic", sub: "Análise de histórico e vistoria cautelar", data: "08/06/2026", perfil: "Cliente", status: "Em Andamento" },

    { id: "1015", assunto: "Avaliação Cautelar - Porsche Boxster", sub: "Laudo técnico de pintura e motorização", data: "28/05/2026", perfil: "Cliente", status: "Em Andamento" }
];

document.addEventListener("DOMContentLoaded", function() {
    renderizarPainelTickets();
});

function renderizarPainelTickets() {
    const corpoTabela = document.getElementById("listaTicketsCorpo");
    if (!corpoTabela) return;

    corpoTabela.innerHTML = "";

    let total = 0;
    let andamento = 0;
    let concluidos = 0;

    const cargoUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();
    const btnNovoTicket = document.querySelector(".btn-novo-ticket");

    if (cargoUsuario === "admin") {
        if (btnNovoTicket) btnNovoTicket.style.display = "none";
    } else {
        if (btnNovoTicket) btnNovoTicket.style.display = "flex";
    }

    if (bancoDeTickets.length === 0) {
        corpoTabela.innerHTML = `<tr><td colspan="6" style="text-align: center; color: #94a3b8; padding: 40px;">Nenhum ticket encontrado.</td></tr>`;
    }

    bancoDeTickets.forEach(ticket => {
        total++;
        if (ticket.status.toLowerCase() === "em andamento") {
            andamento++;
        } else if (ticket.status.toLowerCase() === "concluído" || ticket.status.toLowerCase() === "concluido" || ticket.status.toLowerCase() === "fechado") {
            concluidos++;
        }

        let classeStatus = "em-andamento";
        if (ticket.status.toLowerCase() === "concluído" || ticket.status.toLowerCase() === "concluido") classeStatus = "concluido";
        if (ticket.status.toLowerCase() === "fechado") classeStatus = "fechado";

        let classePerfil = ticket.perfil.toLowerCase();

        let acoesHtml = `
            <button class="btn-visualizar" onclick="atualizarTicket('${ticket.id}')" style="margin-right: 5px; background-color: #e2e8f0; color: #334155;">
                <i class="fa-solid fa-pen-to-square"></i> Alterar
            </button>
        `;

        if (cargoUsuario === "admin") {
            acoesHtml += `
                <button class="btn-visualizar" onclick="excluirTicket('${ticket.id}')" style="background-color: #fee2e2; color: #ef4444; border: none;">
                    <i class="fa-solid fa-trash-can"></i> Excluir
                </button>
            `;
        }

        const tr = document.createElement("tr");
        tr.id = `ticket-row-${ticket.id}`;
        tr.innerHTML = `
            <td style="font-weight: 700; color: #1e2e77;">#${ticket.id}</td>
            <td>
                <span style="font-weight: 600; display: block; color: #0f172a;">${ticket.assunto}</span>
                <span class="subtexto-ticket">${ticket.sub}</span>
            </td>
            <td>${ticket.data}</td>
            <td><span class="tag-perfil ${classePerfil}">${ticket.perfil}</span></td>
            <td><span class="status-badge ${classeStatus}">${ticket.status}</span></td>
            <td class="texto-direita" style="white-space: nowrap;">${acoesHtml}</td>
        `;
        corpoTabela.appendChild(tr);
    });

    document.getElementById("qtdTotal").innerText = total;
    document.getElementById("qtdAndamento").innerText = andamento;
    document.getElementById("qtdConcluidos").innerText = concluidos;
}

function atualizarTicket(id) {
    const ticket = bancoDeTickets.find(t => t.id === id);
    if (!ticket) return;

    const novoAssunto = prompt("Alterar assunto principal do veículo:", ticket.assunto);
    if (novoAssunto === null) return;

    const novaDesc = prompt("Alterar breve descrição do serviço:", ticket.sub);
    if (novaDesc === null) return;

    const novoStatus = prompt("Alterar status (Em Andamento, Concluído, Fechado):", ticket.status);
    if (novoStatus === null) return;

    ticket.assunto = novoAssunto || ticket.assunto;
    ticket.sub = novaDesc || ticket.sub;
    ticket.status = novoStatus || ticket.status;

    renderizarPainelTickets();
}

function excluirTicket(id) {
    const cargoUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();
    
    if (cargoUsuario !== "admin") {
        alert("Acesso negado: Clientes não podem excluir tickets.");
        return;
    }

    const ticketEncontrado = bancoDeTickets.find(t => t.id === id);
    const assuntoTicket = ticketEncontrado ? ticketEncontrado.assunto : "este ticket";

    const confirmar = confirm(`Tem certeza que deseja excluir o Ticket #${id}?\n(${assuntoTicket})`);
    
    if (confirmar) {
        bancoDeTickets = bancoDeTickets.filter(ticket => ticket.id !== id);
        renderizarPainelTickets();
    }
}

function criarNovoTicket() {
    const cargoUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();

    if (cargoUsuario === "admin") {
        alert("Acesso negado: Administradores não criam tickets por aqui.");
        return;
    }

    const assunto = prompt("Digite o assunto principal do veículo:");
    if (!assunto) return;

    const sub = prompt("Escreva uma breve descrição do serviço:");
    if (!sub) return;

    const novoId = Math.floor(Math.random() * 900) + 1000;
    const dataAtual = new Date().toLocaleDateString('pt-BR');
    const perfilSalvo = localStorage.getItem("perfilUsuario") || "Cliente";

    const novoTicket = {
        id: novoId.toString(),
        assunto: assunto,
        sub: sub,
        data: dataAtual,
        perfil: perfilSalvo,
        status: "Em Andamento"
    };

    bancoDeTickets.unshift(novoTicket);
    renderizarPainelTickets();
}