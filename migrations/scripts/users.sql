CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL ,
    password varchar(255) NOT NULL
);
