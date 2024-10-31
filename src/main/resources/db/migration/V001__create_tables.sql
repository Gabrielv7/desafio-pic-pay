CREATE TABLE tb_wallet (
    id UUID PRIMARY KEY NOT NULL,
    balance NUMERIC(38, 2) NOT NULL,
    creation_date TIMESTAMP(6),
    last_update_date TIMESTAMP(6)
);

CREATE TABLE tb_user (
    id SERIAL PRIMARY KEY NOT NULL,
    creation_date TIMESTAMP(6),
    document VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) NOT NULL,
    last_update_date TIMESTAMP(6),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(255) NOT NULL,
    wallet_id UUID NOT NULL,
    FOREIGN KEY (wallet_id) references tb_wallet (id)
);

-- Criação da tabela tb_transaction
CREATE TABLE tb_transaction (
    id UUID PRIMARY KEY NOT NULL,
    amount NUMERIC(38, 2) NOT NULL,
    creation_date TIMESTAMP(6),
    flag_estorno BOOLEAN NOT NULL,
    last_update_date TIMESTAMP(6),
    payee INTEGER NOT NULL,
    payer INTEGER NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES tb_user (id)
);