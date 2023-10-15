CREATE TABLE transactions(
    transaction_id SERIAL PRIMARY KEY,
    amount INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    wallet_id INT,
    FOREIGN KEY (wallet_id) REFERENCES wallets(wallet_id)
);
