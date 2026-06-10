function toggleMenuLateral() {
    const menuLat = document.getElementById("menuLateralPainel");
    if (menuLat) {
        menuLat.classList.toggle("aberto");
    }
}

function deslogarEVoltar() {
    localStorage.removeItem("nomeUsuario");
    localStorage.removeItem("emailUsuario");
    localStorage.removeItem("perfilUsuario");
    localStorage.removeItem("usuarioLogado");

    const noIndexPrincipal = window.location.pathname.endsWith('index.html') || window.location.pathname === '/' || window.location.pathname.split('/').pop() === '';
    
    if (noIndexPrincipal) {
        window.location.href = "index.html";
    } else {
        window.location.href = "../../index.html";
    }
}

document.addEventListener("DOMContentLoaded", function() {
    const usuarioLogado = localStorage.getItem("usuarioLogado");
    
    if (usuarioLogado === "true") {
        const btnAuthOriginal = document.getElementById("btnAuthHeader");
        if (btnAuthOriginal) {
            btnAuthOriginal.style.display = "none";
            
            const ulMenu = btnAuthOriginal.closest('ul');
            if (ulMenu) {
                const liMenuGatilho = document.createElement("li");
                liMenuGatilho.id = "li-menu-gatilho";
                liMenuGatilho.innerHTML = `<button class="btn-menu-painel-gatilho" onclick="toggleMenuLateral()">☰ Menu</button>`;
                ulMenu.appendChild(liMenuGatilho);
            }
        }

        const noIndexPrincipal = window.location.pathname.endsWith('index.html') || window.location.pathname === '/' || window.location.pathname.split('/').pop() === '';
        const prefixoUrl = noIndexPrincipal ? "" : "../../";

        const cargoUsuario = (localStorage.getItem("perfilUsuario") || "Cliente").toLowerCase();
        let linksDinamicosHTML = "";

        if (cargoUsuario === "admin") {
         
            linksDinamicosHTML = `
                <a href="${prefixoUrl}index.html" class="item-menu-lat"><i class="fa-solid fa-house"></i> Início</a>
                <a href="${prefixoUrl}frontend/ticket/ticket.html" class="item-menu-lat"><i class="fa-solid fa-folder-open"></i> Visualizar Tickets</a>
                <a href="${prefixoUrl}frontend/estoque/estoque.html" class="item-menu-lat"><i class="fa-solid fa-car"></i> Ver Estoque</a>
            `;
        } else {
       
            linksDinamicosHTML = `
                <a href="${prefixoUrl}index.html" class="item-menu-lat"><i class="fa-solid fa-house"></i> Início</a>
                <a href="${prefixoUrl}frontend/ticket/ticket.html" class="item-menu-lat"><i class="fa-solid fa-folder-open"></i> Meus Tickets</a>
                <a href="${prefixoUrl}frontend/estoque/estoque.html" class="item-menu-lat"><i class="fa-solid fa-car"></i> Ver Estoque</a>
            `;
        }

        const menuHTML = `
        <aside class="menu-lateral-painel" id="menuLateralPainel">
            <div class="topo-menu-lateral">
                <button class="btn-fechar-menu" onclick="toggleMenuLateral()">✕</button>
                <img src="${prefixoUrl}frontend/img/logovelox.png" alt="Logo Velox" class="logo-menu-lat">
            </div>
            <hr class="divisor-menu-lat">
            <nav class="links-menu-lat">
                ${linksDinamicosHTML}
            </nav>
            <div class="rodape-usuario-lat">
                <div class="usuario-info-lat">
                    <div class="avatar-lat">
                        <i class="fa-solid fa-user"></i>
                    </div>
                    <div class="dados-texto-lat">
                        <span class="nome-lat" id="nomeUserPainel">Carregando...</span>
                        <span class="email-lat" id="emailUserPainel">...</span>
                    </div>
                </div>
                <button class="btn-sair-lat" onclick="deslogarEVoltar()" title="Sair da conta" style="display: flex; align-items: center; gap: 5px; background: none; border: none; color: #ffffff; font-size: 14px; font-weight: bold; cursor: pointer;">
                    <span style="font-size: 12px; color: rgba(255,255,255,0.8);">Sair</span>
                    <i class="fa-solid fa-right-from-bracket" style="font-size: 16px;"></i>
                </button>
            </div>
        </aside>`;

        document.body.insertAdjacentHTML('beforeend', menuHTML);

        const nomeSalvo = localStorage.getItem("nomeUsuario");
        const emailSalvo = localStorage.getItem("emailUsuario");
        const campoNome = document.getElementById("nomeUserPainel");
        const campoEmail = document.getElementById("emailUserPainel");

        if (nomeSalvo && emailSalvo && campoNome && campoEmail) {
            campoNome.innerText = nomeSalvo;
            campoEmail.innerText = emailSalvo;
        }
    }
});