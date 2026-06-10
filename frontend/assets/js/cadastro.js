
const formCadastro = document.getElementById("formCadastro");
const linkEntrar = document.getElementById("linkEntrar");
const navBtnEntrar = document.getElementById("navBtnEntrar");


formCadastro.addEventListener("submit", function(event) {
    event.preventDefault(); 

    const nome = document.getElementById("nome").value;
    const sobrenome = document.getElementById("sobrenome").value;
    const telefone = document.getElementById("telefone").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;

 
    window.location.href = "entrar.html";
});

linkEntrar.addEventListener("click", function(event) {
    event.preventDefault();
    window.location.href = "entrar.html";
});

if (navBtnEntrar) {
    navBtnEntrar.addEventListener("click", function() {
        window.location.href = "entrar.html";
    });
}


const btnMenu = document.getElementById("btnMenu");
const menuNavegacao = document.getElementById("menuNavegacao");

if (btnMenu && menuNavegacao) {
    btnMenu.addEventListener("click", () => {
        menuNavegacao.classList.toggle("ativo");
    });
}