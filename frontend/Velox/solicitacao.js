const formulario = document.getElementById('formularioOrcamento');
const tipoServico = document.querySelectorAll('input[name="tipoServico"]');
const detalhesVeiculo = document.getElementById('detalhesVeiculo');
const mensagemSucesso = document.getElementById('mensagemSucesso');
const btnEnviar = document.querySelector('.btn-enviar');

tipoServico.forEach(radio => {
    radio.addEventListener('change', function() {
        if (this.value === 'Compra de Veículo' || this.value === 'Venda de Veículo' || this.value === 'Outro') {
            detalhesVeiculo.style.display = 'block';
            alterarObrigatoriedadeCampos(true);
        } else if (this.value === 'Consultoria') {
            detalhesVeiculo.style.display = 'none';
            alterarObrigatoriedadeCampos(false);
            limparCamposVeiculo();
        }
    });
});

function alterarObrigatoriedadeCampos(obrigatorio) {
    document.getElementById('marca').required = obrigatorio;
    document.getElementById('modelo').required = obrigatorio;
    document.getElementById('ano').required = obrigatorio;
    document.getElementById('orcamento').required = obrigatorio;
    document.getElementById('condicao').required = obrigatorio;
}

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
          
            alert('Ops! Houve um problema ao enviar o formulário. Por favor, tente novamente.');
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