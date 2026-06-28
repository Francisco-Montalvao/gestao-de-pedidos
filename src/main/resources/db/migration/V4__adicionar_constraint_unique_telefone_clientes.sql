ALTER TABLE clientes
    ADD CONSTRAINT uk_clientes_telefone UNIQUE (telefone);