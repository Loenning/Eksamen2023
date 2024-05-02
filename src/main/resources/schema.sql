drop table if exists citizens;
CREATE TABLE citizens
(
    firstName   VARCHAR(50)  NOT NULL,
    surname     VARCHAR(50)  NOT NULL,
    dateOfBirth VARCHAR(20)  NOT NULL,
    ssn         VARCHAR(50) PRIMARY KEY,
    phoneNumber VARCHAR(20)  NOT NULL,
    email       VARCHAR(100) NOT NULL,
    city        VARCHAR(50)  NOT NULL,
    street      VARCHAR(100) NOT NULL
);

CREATE TABLE users
(
    id       INTEGER AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

