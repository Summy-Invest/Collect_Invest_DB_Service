CREATE TABLE stock_portfolio(
    order_id SERIAL PRIMARY KEY,
    order_date timestamp,
    collectible_id INT,
    user_id INT,
    transaction_id INT,
    FOREIGN KEY (collectible_id) REFERENCES users(user_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id)
);

