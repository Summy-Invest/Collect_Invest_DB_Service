CREATE TABLE stock_portfolio(
    order_id SERIAL PRIMARY KEY,
    order_date timestamp,
    shares_count INT,
    collectible_id INT,
    user_id INT,
    total_price FLOAT,
    transaction_id INT,
    FOREIGN KEY (collectible_id) REFERENCES users(user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

