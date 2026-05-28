# 🚗 Consultoria Velox

> Sistema web de consultoria automotiva desenvolvido como Projeto Integrador do 1º semestre de ADS — UNISENAI Campus SCS Boa Vista.

O sistema permite que clientes solicitem serviços de **compra, venda e consultoria** de veículos através de um sistema de tickets, com painel administrativo para gerenciamento de solicitações e estoque.

---

## 👥 Equipe

| Função | Integrante |
|--------|------------|
| Product Manager | Luana |
| Product Owner | Karen |
| Tech Lead / Back-End Developer | Victor Moreno |
| Front-End Developer | Steffany |

**Contratante:** Consultoria Velox  
**Ponto de contato do cliente:** Karen

---

## 🗂️ Estrutura do Repositório

```
Consultoria-Velox/
│
├── docs/                          # Documentação (Engenharia de Software)
│   ├── Especificacao_Software.docx  # Especificação completa + casos de uso + MER
│   └── TAP.docx                     # Termo de Abertura do Projeto
│
├── frontend/                      # Desenvolvimento Web Front-End
│   ├── index.html
│   ├── css/
│   ├── js/
│   └── assets/
│
├── database/                      # Banco de Dados
│   ├── MER.png                      # Modelo Entidade-Relacionamento
│   └── script.sql                   # Script de criação das tabelas
│
├── src/                           # Linguagem de Programação (Java)
│   ├── telas/
│   │   ├── TelaLogin.java
│   │   └── TelaCadastro.java
│
├── network/                       # TI e Conectividade
│   └── diagrama_rede.pkt            # Diagrama de rede (Cisco Packet Tracer)
│
└── README.md
```

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|--------|------------|
| Back-End | Java + Spring Boot |
| Front-End | HTML, CSS, JavaScript |
| Banco de Dados | MySQL |
| Prototipação | Figma |
| Versionamento | Git / GitHub |
| Rede | Cisco Packet Tracer |

---

## 🎨 Identidade Visual

| | Cor |
|-|-----|
| Principal | `#3A5AE6` |
| Fundo | `#FFFDFA` |
| Secundárias | `#3756DD` `#2F48BB` `#263B99` `#1E2E77` `#152155` |

**Fonte:** Epilogue  
**Protótipo:** [Figma — Velox](https://www.figma.com/proto/pt1Ub7h5QC2Tz7m7ANVCK7/Velox?node-id=6-8)

---

## 🗄️ Banco de Dados

O banco é composto por 5 tabelas:

- `tb_usuarios` — cadastro de usuários do sistema
- `tb_cargos` — cargos disponíveis (CLIENTE, GESTOR, ADMIN)
- `tb_usuario_cargos` — relacionamento entre usuários e cargos
- `tb_estoque_veiculo` — veículos disponíveis cadastrados pelo gestor
- `tb_tickets` — solicitações de serviço abertas pelos clientes

---

## 📋 Funcionalidades

- Cadastro e login de clientes
- Sistema de tickets para solicitação de serviços (Compra, Venda, Consultoria)
- Acompanhamento de status do ticket por e-mail
- Cancelamento de ticket pelo cliente ou gestor
- Painel administrativo para gerenciamento de tickets e estoque
- Controle de acesso por cargos (CLIENTE, GESTOR, ADMIN)

---

## 🏫 Informações Acadêmicas

| | |
|-|-|
| Instituição | UNISENAI — Campus SCS Boa Vista |
| Curso | Tecnólogo em Análise e Desenvolvimento de Sistemas |
| Componente | Projeto Integrador — 1º Semestre |
| Professores | Daniel, Luiz, Luyra e Roberto |
| Apresentação | Semana de 12 de junho de 2026 |
