CREATE TABLE transactions(
    transaction_id SERIAL PRIMARY KEY,
    amount NUMERIC(15, 2) NOT NULL,
    status VARCHAR(255) NOT NULL DEFAULT 'In Progress',
    wallet_id INT,
    FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id)
);
