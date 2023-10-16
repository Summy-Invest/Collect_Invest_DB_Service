CREATE TABLE wallets(
    wallet_id SERIAL PRIMARY KEY,
    user_id INT,
    money INT DEFAULT 0 CHECK (money >= 0),
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
