CREATE TABLE categories(
    price_id SERIAL PRIMARY KEY,
    price INT,
    collectable_id INT,
    FOREIGN KEY (collectable_id) REFERENCES collectibles(collectible_id)
);
