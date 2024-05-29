CREATE DATABASE IF NOT EXISTS SellPro;
USE SellPro;

CREATE TABLE IF NOT EXISTS Users (
    id        INTEGER     PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(30) NOT NULL,
    firstName VARCHAR(15),
    email     VARCHAR(20) UNIQUE NOT NULL,
    password  VARCHAR(60) NOT NULL
);

CREATE TABLE IF NOT EXISTS Products (
    id          INTEGER      PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(25)  NOT NULL,
    description VARCHAR(255),
    price       DOUBLE       NOT NULL CHECK(price >= 0),
    in_stock    INTEGER      DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Sales (
    id          INTEGER     PRIMARY KEY AUTO_INCREMENT,
    customer    VARCHAR(30) NOT NULL,
    date        DATE        NOT NULL,
    time        TIME        NOT NULL,
    user_id     INTEGER
);

CREATE TABLE IF NOT EXISTS SaleProducts(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    quantity   INTEGER DEFAULT 1 CHECK(quantity >= 0),
    product_id INTEGER NOT NULL,
    sale_id    INTEGER NOT NULL
);

ALTER TABLE Sales
ADD CONSTRAINT FK_Sales_00
FOREIGN KEY(user_id) REFERENCES Users(id);

ALTER TABLE SaleProducts
ADD CONSTRAINT FK_SaleProducts_00
FOREIGN KEY(product_id) REFERENCES Products(id);

ALTER TABLE SaleProducts
ADD CONSTRAINT FK_SaleProducts_01
FOREIGN KEY(sale_id) REFERENCES Sales(id);

INSERT INTO Users(name, firstName, email, password)
VALUES('Admin', '', 'admin@sellpro.com', 'admin');
