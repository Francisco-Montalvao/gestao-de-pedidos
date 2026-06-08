CREATE TABLE clientes
(
    id        BIGSERIAL PRIMARY KEY,
    nome      VARCHAR(100) not null,
    email     VARCHAR(100) not null UNIQUE,
    telefone  varchar(50)  not null,
    criado_em timestamp    not null default current_timestamp
);


create table categorias
(
    id   bigserial primary key,
    nome varchar(50) not null unique

);

create table produtos
(
    id           bigserial primary key,
    nome         varchar(100) unique not null,
    descricao    varchar(200),
    preco        numeric(10, 2)      not null check ( preco > 0 ),
    estoque      integer             not null check ( estoque >= 0 ),
    categoria_id bigint              not null references categorias (id),
    ativo        boolean             not null default true,
    criado_em    timestamp           not null default current_timestamp
);

create table pedidos
(
    id            bigserial primary key,
    cliente_id    bigint         not null references clientes (id),
    status        varchar(20)    not null default 'PENDENTE' CHECK ( status IN
                                                                     ('PENDENTE', 'CONFIRMADO', 'EM_PREPARO', 'ENVIADO',
                                                                      'ENTREGUE', 'CANCELADO') ),
    valor_total   numeric(10, 2) not null default 0,
    criado_em     timestamp      not null default current_timestamp,
    atualizado_em TIMESTAMP
);

create table itens_pedido
(
    id             bigserial primary key,
    pedido_id      bigint         not null references pedidos (id),
    produto_id     bigint         not null references produtos (id),
    quantidade     integer         not null check ( quantidade > 0 ),
    preco_unitario numeric(10, 2) not null check ( preco_unitario >= 0 )
);

