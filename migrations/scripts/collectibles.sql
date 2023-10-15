CREATE TABLE collectibles(
    collectible_id SERIAL PRIMARY KEY,
    name varchar(255),
    description text,
    photo bytea,
    category_id int,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
