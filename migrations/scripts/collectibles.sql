CREATE TABLE collectibles(
    collectible_id SERIAL PRIMARY KEY,
    name varchar(255),
    description text,
    category VARCHAR(255),
    photo_url varchar(255),
    current_price NUMERIC(15, 2),
    available_shares int
);
