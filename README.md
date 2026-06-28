# 📦 Gestão de Pedidos API

![Status](https://img.shields.io/badge/status-production--ready-brightgreen)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

Uma API RESTful robusta para o gerenciamento de um sistema de pedidos, desenvolvida com foco em **integridade de domínio** e **previsibilidade de comportamento**.

## ✨ Sobre o Projeto

Este projeto foi construído aplicando conceitos modernos de engenharia de software para garantir um código limpo, manutenível e escalável.

- **Arquitetura em Camadas (Layered Architecture):** Separação clara entre as camadas de `Controller` (Interface), `Service` (Regras de Negócio) e `Repository` (Acesso a Dados).
- **Domain-Driven Design (DDD):**
  - **Value Objects:** Utilização de `Java Records` para tipos como `Email`, `NomePessoa`, `NomeProduto` e `Telefone`, garantindo imutabilidade e validação de dados no momento da criação.
  - **Rich Domain Model:** As entidades (`Pedido`, `Produto`) possuem lógica de negócio intrínseca (ex: transição de status, controle de estoque), evitando o antipadrão de modelos anêmicos.
- **Tratamento de Exceções:** Implementação de um `@ControllerAdvice` para capturar exceções de negócio e retornar respostas padronizadas e amigáveis.

---

## 🚀 Funcionalidades Implementadas

- ✅ **Gestão de Clientes:** CRUD completo com validação de dados.
- ✅ **Gestão de Pedidos:** 
  - Fluxo de criação com validação de disponibilidade de estoque.
  - Gestão de ciclo de vida através de transições de status controladas.
  - Cancelamento de pedidos com estorno automático de estoque.
- ✅ **Controle de Estoque:** Gerenciamento integrado diretamente no modelo de domínio.
- ✅ **Migrações de Banco de Dados:** Versionamento de schema profissional utilizando **Flyway**.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Uso |
| :--- | :--- |
| **Java 21** | Linguagem base (LTS) |
| **Spring Boot 3** | Framework principal |
| **Spring Data JPA** | Persistência de dados |
| **PostgreSQL** | Banco de dados relacional |
| **Flyway** | Evolução de schema |
| **Maven** | Gerenciador de dependências |
| **Jakarta Validation** | Garantia de integridade dos contratos |

---

## 📑 Endpoints da API

A API utiliza o padrão **snake_case** para todos os campos em JSON.

### Clientes
| Verbo  | Endpoint         | Descrição                  |
| :----- | :--------------- | :------------------------- |
| `POST` | `/clientes`      | Cadastra um novo cliente.  |
| `GET`  | `/clientes`      | Lista todos os clientes.   |
| `GET`  | `/clientes/{id}` | Busca um cliente por ID.   |

**Exemplo de corpo (`POST /clientes`):**
```json
{
  "nome": "Carlos Mendes",
  "email": "carlos.mendes@email.com",
  "telefone": "38999112233"
}
```

### Pedidos
| Verbo   | Endpoint                   | Descrição                               |
| :------ | :------------------------- | :-------------------------------------- |
| `POST`  | `/pedidos`                 | Cria um novo pedido.                    |
| `GET`   | `/pedidos`                 | Lista todos os pedidos.                 |
| `DELETE`| `/pedidos/{id}`            | Cancela um pedido (estorno de estoque). |

**Exemplo de corpo (`POST /pedidos`):**
```json
{
  "cliente_id": 1,
  "itens": [
    {
      "produto_id": 10,
      "quantidade": 2
    }
  ]
}
```

---

## ⚙️ Como Executar o Projeto

1. **Clone o repositório:**
   ```sh
   git clone <url-do-seu-repositorio>
   ```

2. **Configuração:**
   Ajuste as credenciais do banco de dados em `src/main/resources/application.properties`.

3. **Execução:**
   ```sh
   mvn spring-boot:run
   ```

---

## 🗺️ Roadmap de Evolução

- [ ] **QA:** Implementação de suíte de testes (Unitários de domínio e Integração de API).
- [ ] **Security:** Implementação de autenticação via JWT.
- [ ] **Observability:** Integração com métricas e monitoramento.
- [ ] **DevOps:** Containerização e Pipeline de CI/CD.
```,file_path: