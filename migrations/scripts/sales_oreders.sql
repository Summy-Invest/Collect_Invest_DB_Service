CREATE TABLE sales_oreders(
    order_id SERIAL PRIMARY KEY,
    user_id int,
    name varchar(255),
    description text,
    price int,
    category_id int,
    photo bytea,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
