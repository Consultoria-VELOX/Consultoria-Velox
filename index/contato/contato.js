document.addEventListener("DOMContentLoaded", function () {
    const formulario = document.getElementById("formContato");
    const caixaStatus = document.getElementById("statusMensagem");
    const botaoEnviar = document.getElementById("btnEnviar");

    if (formulario) {
        formulario.addEventListener("submit", async function (evento) {
            evento.preventDefault(); 

          
            botaoEnviar.disabled = true;
            botaoEnviar.innerText = "Enviando...";
            
            const dadosDoForm = new FormData(formulario);

            try {
                const resposta = await fetch(formulario.action, {
                    method: formulario.method,
                    body: dadosDoForm,
                    headers: {
                        'Accept': 'application/json'
                    }
                });

                if (resposta.ok) {
                 
                    caixaStatus.className = "status-mensagem sucesso";
                    caixaStatus.innerHTML = "✓ Sua mensagem foi enviada com sucesso! Nossa equipe entrará em contato em breve.";
                    formulario.reset(); 
                } else {
                   
                    caixaStatus.className = "status-mensagem erro";
                    caixaStatus.innerHTML = "✕ Não foi possível enviar. Por favor, verifique os dados ou tente mais tarde.";
                }
            } catch (erro) {
                
                caixaStatus.className = "status-mensagem erro";
                caixaStatus.innerHTML = "✕ Erro de conexão. Verifique sua internet e tente novamente.";
            } finally {
                
                botaoEnviar.disabled = false;
                botaoEnviar.innerText = "Enviar Mensagem";
            }
        });
    }
});