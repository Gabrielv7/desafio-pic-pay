-- Criação da tabela tb_wallet
CREATE TABLE tb_wallet (
    id UUID NOT NULL,
    balance NUMERIC(38, 2) NOT NULL,
    creation_date TIMESTAMP(6),
    last_update_date TIMESTAMP(6),
    PRIMARY KEY (id)
);

-- Criação da tabela tb_user
CREATE TABLE tb_user (
    id SERIAL NOT NULL,
    creation_date TIMESTAMP(6),
    document VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    last_update_date TIMESTAMP(6),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(255) NOT NULL,
    wallet_id UUID NOT NULL,
    PRIMARY KEY (id)
);

-- Constraint de chave única para o campo document
ALTER TABLE IF EXISTS tb_user
ADD CONSTRAINT UK_qpa44khx2fwome9ophiuyeaj8 UNIQUE (document);

-- Constraint de chave estrangeira para wallet_id referenciando tb_wallet
ALTER TABLE IF EXISTS tb_user
ADD CONSTRAINT FK7pqxlymly84p14nvkbgpikc1u FOREIGN KEY (wallet_id) REFERENCES tb_wallet (id);

-- Criação da tabela tb_transaction
CREATE TABLE tb_transaction (
    id UUID NOT NULL,
    amount NUMERIC(38, 2) NOT NULL,
    creation_date TIMESTAMP(6),
    flag_estorno BOOLEAN NOT NULL,
    last_update_date TIMESTAMP(6),
    payee INTEGER NOT NULL,
    payer INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id INTEGER,
    PRIMARY KEY (id)
);

-- Constraint de chave estrangeira para user_id referenciando tb_user
ALTER TABLE IF EXISTS tb_transaction
ADD CONSTRAINT FK7f3wtdpf61kpwpm1p7ygh2ccf FOREIGN KEY (user_id) REFERENCES tb_user (id);
