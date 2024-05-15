drop table if exists citizens;
CREATE TABLE citizens
(
    cid         INTEGER AUTO_INCREMENT NOT NULL,
    firstName   VARCHAR(50)  NOT NULL,
    lastName     VARCHAR(50)  NOT NULL,
    dateOfBirth VARCHAR(50)   NOT NULL,
    ssn         VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(20)  NOT NULL,
    email       VARCHAR(100) NOT NULL,
    city        VARCHAR(50)  NOT NULL,
    street      VARCHAR(100) NOT NULL,
    PRIMARY KEY (cid)
);

CREATE TABLE users
(
    userid INTEGER AUTO_INCREMENT NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    PRIMARY KEY (userid)
);

INSERT INTO users(username, password) VALUES ('Admin', 'Admin123');

