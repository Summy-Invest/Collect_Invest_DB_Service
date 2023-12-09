CREATE TABLE collectibles(
    collectible_id SERIAL PRIMARY KEY,
    name varchar(255),
    description text,
    category_id int,
    photo_link varchar(255),
    current_price float,
    available_shares int,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
