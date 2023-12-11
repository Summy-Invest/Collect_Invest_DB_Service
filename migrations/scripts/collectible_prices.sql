CREATE TABLE collectible_prices(
    price_id SERIAL PRIMARY KEY,
    date timestamp,
    price NUMERIC(15, 2),
    collectible_id int,
    FOREIGN KEY (collectible_id) REFERENCES collectibles(collectible_id)
);
