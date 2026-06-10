
const formulario = document.getElementById('formularioOrcamento');
const tipoServico = document.querySelectorAll('input[name="tipoServico"]');
const detalhesVeiculo = document.getElementById('detalhesVeiculo');
const mensagemSucesso = document.getElementById('mensagemSucesso');
const btnEnviar = document.querySelector('.btn-enviar');


tipoServico.forEach(radio => {
    radio.addEventListener('change', function() {
        if (this.value === 'Compra de Veículo' || this.value === 'Venda de Veículo' || this.value === 'Outro') {
            detalhesVeiculo.style.display = 'block';
            document.getElementById('marca').required = true;
            document.getElementById('modelo').required = true;
            document.getElementById('ano').required = true;
            document.getElementById('orcamento').required = true;
            document.getElementById('condicao').required = true;
        } else if (this.value === 'Consultoria') {
            detalhesVeiculo.style.display = 'none'; 
            document.getElementById('marca').required = false;
            document.getElementById('modelo').required = false;
            document.getElementById('ano').required = false;
            document.getElementById('orcamento').required = false;
            document.getElementById('condicao').required = false;
            limparCamposVeiculo();
        }
    });
});


function limparCamposVeiculo() {
    document.getElementById('marca').value = '';
    document.getElementById('modelo').value = '';
    document.getElementById('ano').value = '';
    document.getElementById('orcamento').value = '';
    document.getElementById('condicao').value = '';
}

formulario.addEventListener('submit', function(evento) {
   
    evento.preventDefault();

    btnEnviar.innerText = "Enviando...";
    btnEnviar.disabled = true;

    const dados = new FormData(formulario);

  
    fetch(formulario.action, {
        method: formulario.method,
        body: dados,
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(resposta => {
        if (resposta.ok) {
         
            document.querySelector('.grupo-botoes').style.display = 'none';
            
          
            const codigoAleatorio = 'VLX-' + Math.floor(1000 + Math.random() * 9000);
            document.getElementById('codigoRastreamento').innerText = codigoAleatorio;

            mensagemSucesso.style.display = 'block';
            
            mensagemSucesso.scrollIntoView({ behavior: 'smooth' });

         
            formulario.reset();
        } else {
            alert('Ops! Houve um erro no servidor do formulário. Tente novamente mais tarde.');
            resetarBotao();
        }
    })
    .catch(erro => {
        alert('Erro de conexão. Verifique sua internet e tente novamente.');
        resetarBotao();
    });
});

function resetarBotao() {
    btnEnviar.innerText = "Solicitar Orçamento";
    btnEnviar.disabled = false;
}


const botoesAccordion = document.querySelectorAll('.btn-accordion');
botoesAccordion.forEach(botao => {
    botao.addEventListener('click', function(e) {
        e.preventDefault(); 
        const item = this.parentElement;
        item.classList.toggle('ativo');
    });
});