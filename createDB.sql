CREATE DATABASE shoppinglist;
USE shoppinglist;

CREATE TABLE user (
    Nick varchar(10) NOT NULL PRIMARY KEY,
    Password varchar(10) NOT NULL,
    Birth date,
    Gender ENUM('Male', 'Female', 'Non-binary')
);

CREATE TABLE items (
    Owner varchar(10) NOT NULL,
    Name varchar(20) NOT NULL,
    Price DECIMAL(10, 2)
);
