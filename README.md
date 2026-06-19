# Gestão de Pedidos API

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

API RESTful para gerenciamento de um sistema de pedidos, desenvolvida como parte de um desafio de backend. O projeto abrange o cadastro de clientes, produtos, categorias e a gestão completa do ciclo de vida de um pedido.

## ✨ Sobre o Projeto

Este projeto foi construído com foco em boas práticas de desenvolvimento e arquitetura de software, aplicando conceitos modernos para garantir um código limpo, manutenível e escalável.

- **Arquitetura em Camadas (Layered Architecture):** O código é organizado nas camadas `Controller`, `Service` e `Repository`, separando as responsabilidades de API, regras de negócio e acesso a dados.
- **Domain-Driven Design (DDD):** Foram aplicados conceitos de DDD, como:
  - **Value Objects:** Tipos como `Email`, `Nome` e `Telefone` garantem que os dados do domínio sejam sempre válidos e imutáveis.
  - **Rich Domain Model:** As entidades (`Pedido`, `Produto`) contêm a lógica de negócio que lhes pertence (ex: `darBaixaEstoque`, `cancelarPedido`), evitando modelos anêmicos.
- **Tratamento de Exceções:** Um handler global (`@ControllerAdvice`) captura exceções de negócio e de validação, retornando respostas de erro padronizadas para o cliente.

---

## 🚀 Funcionalidades Implementadas

- ✅ **CRUD de Clientes:** Cadastro, listagem, busca, atualização e exclusão de clientes.
- ✅ **Gestão de Pedidos:**
  - Criação de novos pedidos, com validação de cliente e baixa automática de estoque dos produtos.
  - Listagem e busca de pedidos.
  - Cancelamento de pedidos, com estorno automático do estoque.
  - Atualização de status do pedido.
- ✅ **Controle de Estoque:** A lógica de estoque é gerenciada diretamente pelo modelo, garantindo consistência.
- ✅ **Migrações de Banco de Dados:** O [Flyway](https://flywaydb.org/) é utilizado para versionar e aplicar as alterações no schema do banco de dados de forma automática.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Maven** como gerenciador de dependências
- **Flyway** para migrações de banco de dados
- **H2 Database** como banco de dados em memória para desenvolvimento
- **Lombok** para redução de código boilerplate

---

## Endpoints da API

### Clientes

| Verbo  | Endpoint         | Descrição                  |
| :----- | :--------------- | :------------------------- |
| `POST` | `/clientes`      | Cadastra um novo cliente.  |
| `GET`  | `/clientes`      | Lista todos os clientes.   |
| `GET`  | `/clientes/{id}` | Busca um cliente por ID.   |
| `PUT`  | `/clientes/{id}` | Atualiza um cliente.       |
| `DELETE`| `/clientes/{id}`| Deleta um cliente.         |

**Exemplo de body para `POST /clientes`:**
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
| `GET`   | `/pedidos/{id}`            | Busca um pedido por ID.                 |
| `PATCH` | `/pedidos/{id}/cancelar`   | Cancela um pedido e estorna o estoque.  |
| `PATCH` | `/pedidos/{id}/status`     | Atualiza o status de um pedido.         |

**Exemplo de body para `POST /pedidos`:**
```json
{
  "clienteId": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    },
    {
      "produtoId": 10,
      "quantidade": 1
    }
  ]
}
```

---

## ⚙️ Como Executar o Projeto

1.  **Clone o repositório:**
    ```sh
    git clone <url-do-seu-repositorio>
    ```

2.  **Navegue até o diretório do projeto:**
    ```sh
    cd gestao-de-pedidos
    ```

3.  **Execute a aplicação com o Maven:**
    ```sh
    mvn spring-boot:run
    ```

4.  A API estará disponível em `http://localhost:8080`.

5.  **Acessar o banco de dados H2:**
    - Acesse `http://localhost:8080/h2-console` no seu navegador.
    - Use a URL JDBC `jdbc:h2:mem:gestao_de_pedidos`.
    - Deixe o usuário como `sa` e a senha em branco.
