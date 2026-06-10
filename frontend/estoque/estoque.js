const dadosCarros = [
    { nome: "BMW Série 3 Mk7 G20", preco: "R$ 280.000,00", ano: "2022", km: "21.000 km", desc: "Excelente estado de conservação, laudo cautelar 100% aprovado, revisões feitas na concessionária." },
    { nome: "Audi A4 Avant", preco: "R$ 245.000,00", ano: "2020", km: "43.000 km", desc: "Perfeita para a família, motor TFSI potente, teto solar panorâmico e porta-malas espaçoso." },
    { nome: "Mercedes-Benz C300 AMG", preco: "R$ 310.000,00", ano: "2022", km: "18.500 km", desc: "Visual esportivo AMG original, cor cinza fosco de fábrica, interior em alcântara vermelha." },
    { nome: "Porsche Macan T", preco: "R$ 520.000,00", ano: "2023", km: "9.800 km", desc: "Configuração exclusiva, tração integral, som Bose de alta fidelidade e apenas um dono." },
    { nome: "Ford Mustang GT", preco: "R$ 415.000,00", ano: "2021", km: "15.000 km", desc: "Motor V8 puro sangue, escapamento esportivo com seletor de ronco, sem nenhum detalhe." },
    { nome: "Porsche 911 Carrera S", preco: "R$ 890.000,00", ano: "2022", km: "7.400 km", desc: "Verdadeira máquina de pista homologada para rua. Vitrificação de pintura recém feita." },
    { nome: "Audi Q3 Sportback", preco: "R$ 275.000,00", ano: "2022", km: "24.000 km", desc: "Design coupé moderno, tração Quattro, painel Virtual Cockpit updated e faróis Full LED." },
    { nome: "Volvo XC60 T8 Hybrid", preco: "R$ 340.000,00", ano: "2021", km: "31.000 km", desc: "Híbrido plug-in ultra econômico e potente, o SUV mais seguro do mundo com piloto automático adaptativo." },
    { nome: "Range Rover Evoque", preco: "R$ 295.000,00", ano: "2020", km: "38.000 km", desc: "Edição SE, teto contrastante preto, interna bicolor, todas as revisões feitas por tempo." },
    { nome: "Chevrolet Camaro SS", preco: "R$ 380.000,00", ano: "2021", km: "19.000 km", desc: "Conversível imponente para quem busca performance. Rodas aro 20 originais sem ralados." },
    { nome: "Toyota SW4 Diamond", preco: "R$ 365.000,00", ano: "2022", km: "28.500 km", desc: "Versão topo de linha de 7 lugares, motor diesel robusto, interna gelo impecável e muito nova." },
    { nome: "Jeep Commander Overland", preco: "R$ 230.000,00", ano: "2023", km: "14.000 km", desc: "Motor turbodiesel TD380, acabamento premium com suede marrom, som Harman Kardon." }
];

const TELEFONE_LOJA = "5511981090128"; 
const modal = document.getElementById("modalCarro");
const fecharModal = document.getElementById("fecharModal");

document.addEventListener("DOMContentLoaded", function() {
    configurarEventosBotoes();
    verificarPerfilAdmin();
    verificarAdministrador();
    
    const botaoMenu = document.getElementById("botaoMenu");
    if (botaoMenu) {
        botaoMenu.addEventListener("click", () => {
            document.getElementById("menu").classList.toggle("ativo");
        });
    }

    const btnAdicionarCarro = document.getElementById("btnAdicionarCarro");
    if (btnAdicionarCarro) {
        btnAdicionarCarro.addEventListener("click", funcaoAdicionarVeiculoAdmin);
    }
});

function configurarEventosBotoes() {
    const botoesVejaMais = document.querySelectorAll(".btn-veja-mais");
    botoesVejaMais.forEach(botao => {
        botao.removeEventListener("click", abrirModalHandler);
        botao.addEventListener("click", abrirModalHandler);
    });
}

function abrirModalHandler() {
    const index = this.getAttribute("data-index");
    const carro = dadosCarros[index];
    const cardPai = this.parentElement;
    const imagemSrc = cardPai.querySelector("img").src;

    document.getElementById("modalNome").innerText = carro ? carro.nome : cardPai.querySelector("h3").innerText;
    document.getElementById("modalPreco").innerText = carro ? carro.preco : cardPai.querySelector(".preco").innerText;
    
    if (carro) {
        document.getElementById("modalAno").innerText = carro.ano;
        document.getElementById("modalKm").innerText = carro.km;
        document.getElementById("modalDescricao").innerText = carro.desc;
    } else {
        const metas = cardPai.querySelectorAll(".carro-meta span");
        document.getElementById("modalAno").innerText = metas[0] ? metas[0].innerText.trim() : "N/A";
        document.getElementById("modalKm").innerText = metas[1] ? metas[1].innerText.trim() : "N/A";
        document.getElementById("modalDescricao").innerText = "Veículo premium disponível no estoque da Velox.";
    }
    
    document.getElementById("modalImg").src = imagemSrc;

    const nomeCarroText = carro ? carro.nome : cardPai.querySelector("h3").innerText;
    const precoCarroText = carro ? carro.preco : cardPai.querySelector(".preco").innerText;
    const textoZap = `Olá Velox! Vi no estoque do site e tenho interesse na ${nomeCarroText} valor ${precoCarroText}. Poderia me passar mais informações?`;
    document.getElementById("btnWhatsModal").href = `https://api.whatsapp.com/send?phone=${TELEFONE_LOJA}&text=${encodeURIComponent(textoZap)}`;

    modal.classList.add("aberto");
}

function verificarPerfilAdmin() {
    const cargoUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();
    
    if (cargoUsuario === "admin") {
        const containerBtnAdd = document.getElementById("containerBotaoAdicionar");
        if (containerBtnAdd && !document.getElementById("idBtnAdicionarAdmin")) {
            containerBtnAdd.innerHTML = `<button id="idBtnAdicionarAdmin" class="btn-adicionar-dinamico" onclick="funcaoAdicionarVeiculoAdmin()">+ Adicionar Novo Veículo</button>`;
        }

        const cards = document.querySelectorAll(".card-carro");
        cards.forEach(card => {
            if (!card.querySelector(".btn-admin-excluir")) {
                const btnExcluir = document.createElement("button");
                btnExcluir.className = "btn-admin-excluir";
                btnExcluir.innerText = "Remover do Estoque";
                btnExcluir.onclick = function() {
                    if (confirm(`Remover "${card.querySelector("h3").innerText}" do estoque?`)) {
                        card.remove();
                    }
                };
                card.appendChild(btnExcluir);
            }
        });
    }
}

function verificarAdministrador() {
    const perfilUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();
    const btnAdicionar = document.getElementById("btnAdicionarCarro");
    
    if (btnAdicionar) {
        if (perfilUsuario === "admin" || perfilUsuario === "administrador") {
            btnAdicionar.style.display = 'block';
        } else {
            btnAdicionar.style.display = 'none';
        }
    }
}

function funcaoAdicionarVeiculoAdmin() {
    const nome = prompt("Nome/Modelo do Veículo:");
    if (!nome) return;
    const preco = prompt("Preço (Ex: R$ 250.000,00):");
    if (!preco) return;
    const ano = prompt("Ano:");
    if (!ano) return;
    const km = prompt("Quilometragem (Ex: 15000):");
    if (!km) return;
    const imgUrl = prompt("URL da imagem (Cole um link válido ou deixe em branco):") || "https://images.unsplash.com/photo-1549399542-7e3f8b79c341?auto=format&fit=crop&q=80&w=500";

    const grid = document.getElementById("gridEstoqueContem");
    if (!grid) return;

    const novoIndex = document.querySelectorAll(".card-carro").length;
    
    dadosCarros.push({
        nome: nome,
        preco: preco,
        ano: ano,
        km: km + " km",
        desc: "Veículo premium recém-adicionado ao estoque."
    });

    const card = document.createElement("div");
    card.className = "card-carro";
    card.innerHTML = `
        <img src="${imgUrl}" alt="${nome}">
        <h3>${nome}</h3>
        <div class="preco">${preco}</div>
        <div class="carro-meta">
            <span><i class="fa-solid fa-calendar-days"></i> ${ano}</span>
            <span><i class="fa-solid fa-gauge"></i> ${km}</span>
        </div>
        <button class="btn-veja-mais" data-index="${novoIndex}">Veja Mais</button>
    `;

    grid.insertBefore(card, grid.firstChild);
    
    configurarEventosBotoes();
    verificarPerfilAdmin();
    alert("Veículo adicionado com sucesso!");
}

if (fecharModal) {
    fecharModal.addEventListener("click", () => modal.classList.remove("aberto"));
}

window.addEventListener("click", (e) => { 
    if(e.target === modal) modal.classList.remove("aberto"); 
});

window.addEventListener('load', verificarAdministrador);