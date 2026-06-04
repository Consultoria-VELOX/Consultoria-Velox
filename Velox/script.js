
const botaoMenu = document.getElementById('botaoMenu');
const menu = document.getElementById('menu');

botaoMenu.addEventListener('click', function(e) {
    e.stopPropagation();
    menu.classList.toggle('ativo');
});


const linksMenu = menu.querySelectorAll('a');
linksMenu.forEach(link => {
    link.addEventListener('click', function() {
        menu.classList.remove('ativo');
    });
});


document.addEventListener('click', function(evento) {
    if (!evento.target.closest('.navegacao')) {
        menu.classList.remove('ativo');
    }
});


const btnAnterior = document.getElementById('btnAnterior');
const btnProximo = document.getElementById('btnProximo');
const containerCarrossel = document.getElementById('containerCarrossel');
const distanciaScroll = 220;

btnAnterior.addEventListener('click', function() {
    containerCarrossel.scrollBy({
        left: -distanciaScroll,
        behavior: 'smooth'
    });
});

btnProximo.addEventListener('click', function() {
    containerCarrossel.scrollBy({
        left: distanciaScroll,
        behavior: 'smooth'
    });
});


document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});


const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
});


const secoes = document.querySelectorAll('.sobre-nos, .servicos, .portfolio, .depoimentos, .contato');
secoes.forEach(secao => {
    secao.style.opacity = '0';
    secao.style.transform = 'translateY(20px)';
    secao.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
    observer.observe(secao);
});


const botoesConhecerMais = document.querySelectorAll('.btn-saiba-mais');

botoesConhecerMais.forEach(botao => {
    botao.addEventListener('click', function(e) {
      
        this.style.transform = 'scale(0.98)';
        setTimeout(() => {
            this.style.transform = '';
        }, 200);
    });
});

console.log('✅ Página Velox carregado com sucesso!');