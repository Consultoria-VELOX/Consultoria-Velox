const formLogin = document.getElementById("formLogin");
const linkCriarConta = document.getElementById("linkCriarConta");

const CREDENCIAIS = {
    admin: {
        nome: "Administrador Geral",
        email: "admin@velox.com",
        senha: "admin123"
    },
   
    cliente: {
        nome: "Cliente Velox",
        email: "cliente@email.com"
    }
};

formLogin.addEventListener("submit", function(event) {
    event.preventDefault(); 
    
    const nomeDigitado = document.getElementById("nomeCompleto").value.trim();
    const emailDigitado = document.getElementById("email").value.trim();
    const senhaDigitada = document.getElementById("senha").value;
    const perfilSelecionado = document.getElementById("perfil").value;

    if (!perfilSelecionado) {
        alert("Por favor, selecione um perfil de acesso.");
        return;
    }

    if (perfilSelecionado === "admin") {
        if (emailDigitado === CREDENCIAIS.admin.email && senhaDigitada === CREDENCIAIS.admin.senha) {
            alert("Login de Administrador bem-sucedido!");
            
            localStorage.setItem("usuarioLogado", "true");
            localStorage.setItem("perfilUsuario", "admin");
            localStorage.setItem("nomeUsuario", nomeDigitado || CREDENCIAIS.admin.nome);
            localStorage.setItem("emailUsuario", CREDENCIAIS.admin.email);
            
            window.location.href = "../index.html"; 
        } else {
            alert("E-mail ou senha inválidos para o perfil de Administrador!");
        }
    } 
    
    else if (perfilSelecionado === "cliente") {
        alert("Login de Cliente bem-sucedido!");
        
        localStorage.setItem("usuarioLogado", "true");
        localStorage.setItem("perfilUsuario", "cliente");
        localStorage.setItem("nomeUsuario", nomeDigitado || CREDENCIAIS.cliente.nome);
        localStorage.setItem("emailUsuario", emailDigitado || CREDENCIAIS.cliente.email);
        
        window.location.href = "../index.html"; 
    }
});

linkCriarConta.addEventListener("click", function(event) {
    event.preventDefault();
    window.location.href = "../cadastro/cadastro.html"; 
});

const botaoMenu = document.getElementById("botaoMenu");
const menu = document.getElementById("menu");

if (botaoMenu && menu) {
    botaoMenu.addEventListener("click", () => {
        menu.classList.toggle("ativo");
    });
}