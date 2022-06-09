DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;

CREATE TABLE customers
(
    id        SERIAL PRIMARY KEY NOT NULL,
    lastname  VARCHAR(100),
    firstname VARCHAR(100),
    company   VARCHAR(200),
    mail      VARCHAR(255),
    phone     VARCHAR(15),
    mobile    VARCHAR(15),
    notes     TEXT,
    active    BOOLEAN
);

CREATE TABLE orders
(
    id             SERIAL PRIMARY KEY NOT NULL,
    customer_id    INT,
    label          VARCHAR(100),
    adr_et         DECIMAL,
    number_of_days DECIMAL,
    tva            DECIMAL,
    status         VARCHAR(30),
    type           VARCHAR(100),
    notes          TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(30),
    password VARCHAR(255),
    mail     VARCHAR(255),
    grants   VARCHAR(255)
);

INSERT INTO customers (lastname, firstname, company, mail, mobile, notes, active)
VALUES ('JONES', 'Indiana', 'Université de Chicago', 'indiana.jonas@univ-chicago.com', '0666666666',
        'Les notes d''Indiana', true);

INSERT INTO customers (lastname, firstname, company, mail, mobile, notes, active)
VALUES ('KENOBI', 'Obi-Wan', 'Jedis', 'obiwan.kenobi@jedis.com', '0666666666', 'Les notes d''Obi Wan', true);

INSERT INTO customers (lastname, firstname, company, mail, mobile, notes, active)
VALUES ('MCCLANE', 'John', 'NYPD', 'john.mcclane@nypd.com', '0666666666', 'Les notes de John', false);

INSERT INTO customers (lastname, firstname, company, mail, mobile, notes, active)
VALUES ('MCFLY', 'Marty', 'DOC', 'marty.mcfly@doc.com', NULL, 'Les notes de Marty', false);

INSERT INTO orders (customer_id, label, adr_et, number_of_days, tva, status, type, notes)
VALUES (1, 'Formation Java', 450.0, 5, 20, 'En cours', 'Forfait', 'Test');

INSERT INTO orders (customer_id, label, adr_et, number_of_days, tva, status, type, notes)
VALUES (1, 'Formation Spring', 450.0, 3, 20.0, 'En attente', 'Forfait', 'Test');

INSERT INTO orders (customer_id, label, adr_et, number_of_days, tva, status, type, notes)
VALUES (2, 'Formation Jedi', 1500.0, 2, 20.0, 'Payée', 'Forfait', 'Notes sur la formation');

INSERT INTO users (username, password, mail, grants)
VALUES ('admin', '$2y$10$vko34oH32uca.EW2mBgaUO/q53s/cePx0Z/MNbTze7Xdnwkn/1zRi', 'admin@test.fr', 'ADMIN');