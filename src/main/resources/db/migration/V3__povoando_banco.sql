-- 1. Inserindo Clientes (10 Clientes variados)
INSERT INTO clientes (nome, email, telefone) VALUES
                                                 ('Carlos Mendes', 'carlos.mendes@email.com', '38999112233'),
                                                 ('Ana Julia Castro', 'ana.ju@email.com', '38988223344'),
                                                 ('Roberto Almeida', 'beto.almeida@email.com', '38977334455'),
                                                 ('Mariana Silva', 'mari.silva@email.com', '38966445566'),
                                                 ('Fernando Costa', 'nando.costa@email.com', '38955556677'),
                                                 ('Lucia Ferreira', 'lucia.ferreira@email.com', '38944667788'),
                                                 ('Pedro Henrique', 'pedrao.h@email.com', '38933778899'),
                                                 ('Beatriz Souza', 'bia.souza@email.com', '38922889900'),
                                                 ('Gabriel Lima', 'gabi.lima@email.com', '38911990011'),
                                                 ('Renata Gomes', 'renata.g@email.com', '38900001122');

-- 2. Inserindo Categorias (6 Categorias robustas para testar filtros)
INSERT INTO categorias (nome) VALUES
                                  ('Pizzas Tradicionais'),
                                  ('Pizzas Premium'),
                                  ('Pizzas Doces'),
                                  ('Bebidas Não Alcoólicas'),
                                  ('Cervejas'),
                                  ('Entradas');

-- 3. Inserindo Produtos (18 Produtos cobrindo todas as categorias)
INSERT INTO produtos (nome, descricao, preco, estoque, categoria_id) VALUES
-- Tradicionais (ID 1)
('Pizza Calabresa', 'Calabresa fatiada, cebola e mussarela', 45.00, 100, 1),
('Pizza Marguerita', 'Mussarela, tomate e manjericão fresco', 42.00, 100, 1),
('Pizza Frango com Catupiry', 'Frango desfiado com autêntico catupiry', 50.00, 80, 1),
('Pizza Portuguesa', 'Presunto, ovos, cebola, ervilha e mussarela', 48.00, 90, 1),
-- Premium (ID 2)
('Moda do Churrasqueiro', 'Panceta suína crocante, linguiça artesanal e alho poró', 55.00, 50, 2),
('Parma com Rúcula', 'Presunto parma, rúcula fresca e tomate seco', 65.00, 40, 2),
('Quatro Queijos Especial', 'Mussarela, provolone, gorgonzola e parmesão', 52.00, 60, 2),
-- Doces (ID 3)
('Chocolate com Morango', 'Chocolate ao leite com pedaços de morango', 55.00, 30, 3),
('Romeu e Julieta', 'Goiabada cremosa com queijo minas', 50.00, 30, 3),
-- Bebidas Não Alcoólicas (ID 4)
('Coca-Cola 2L', 'Refrigerante garrafa 2 Litros', 12.00, 200, 4),
('Guaraná Antarctica 2L', 'Refrigerante garrafa 2 Litros', 10.00, 150, 4),
('Suco de Laranja 1L', 'Suco natural integral', 15.00, 50, 4),
('Água Mineral 500ml', 'Água sem gás', 5.00, 100, 4),
-- Cervejas (ID 5)
('Heineken Long Neck', 'Cerveja 330ml', 10.00, 120, 5),
('Stella Artois Long Neck', 'Cerveja 330ml', 9.00, 100, 5),
-- Entradas (ID 6)
('Batata Frita com Bacon', 'Porção de 500g com cheddar e bacon', 25.00, 40, 6),
('Pão de Alho Recheado', 'Porção com 4 unidades', 18.00, 60, 6),
('Isca de Frango', 'Porção de 400g empanada', 30.00, 30, 6);

-- 4. Inserindo Pedidos (Vários status e valores pré-calculados)
INSERT INTO pedidos (cliente_id, status, valor_total) VALUES
                                                          (1, 'ENTREGUE', 55.00),   -- Pedido 1
                                                          (2, 'PENDENTE', 97.00),   -- Pedido 2
                                                          (3, 'CANCELADO', 162.00), -- Pedido 3 (Bom para testar a devolução de estoque depois!)
                                                          (4, 'EM_PREPARO', 73.00), -- Pedido 4
                                                          (5, 'ENVIADO', 120.00),   -- Pedido 5
                                                          (6, 'ENTREGUE', 113.00);  -- Pedido 6

-- 5. Inserindo Itens do Pedido (A matemática bate exatamente com o valor_total)

-- Itens do Pedido 1 (Total: 55.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (1, 1, 1, 45.00), -- 1x Calabresa
                                                                                 (1, 11, 1, 10.00); -- 1x Guaraná 2L

-- Itens do Pedido 2 (Total: 97.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (2, 16, 1, 25.00), -- 1x Batata Frita
                                                                                 (2, 7, 1, 52.00),  -- 1x Quatro Queijos
                                                                                 (2, 14, 2, 10.00); -- 2x Heineken (20.00)

-- Itens do Pedido 3 - O CANCELADO (Total: 162.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (3, 2, 3, 42.00),  -- 3x Marguerita (126.00)
                                                                                 (3, 10, 3, 12.00); -- 3x Coca-Cola (36.00)

-- Itens do Pedido 4 (Total: 73.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (4, 5, 1, 55.00),  -- 1x Moda do Churrasqueiro
                                                                                 (4, 17, 1, 18.00); -- 1x Pão de Alho

-- Itens do Pedido 5 (Total: 120.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (5, 6, 1, 65.00),  -- 1x Parma com Rúcula
                                                                                 (5, 8, 1, 55.00);  -- 1x Chocolate com Morango

-- Itens do Pedido 6 (Total: 113.00)
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario) VALUES
                                                                                 (6, 4, 1, 48.00),  -- 1x Portuguesa
                                                                                 (6, 3, 1, 50.00),  -- 1x Frango com Catupiry
                                                                                 (6, 12, 1, 15.00); -- 1x Suco de Laranja