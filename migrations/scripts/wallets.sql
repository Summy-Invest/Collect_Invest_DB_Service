CREATE TABLE wallets(
    wallet_id SERIAL PRIMARY KEY,
    user_id INT,
    money NUMERIC(15, 2) DEFAULT 0 CHECK (money >= 0),
    status VARCHAR(255) DEFAULT 'Active',
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
