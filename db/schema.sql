CREATE EXTENSION pgcrypto;

CREATE TABLE product
(
    id            SERIAL     NOT NULL PRIMARY KEY
  , creation_date TIMESTAMP  NOT NULL
  , name          TEXT       NOT NULL
  , price         REAL       NOT NULL
);

CREATE TABLE "user"
(
    id            SERIAL     NOT NULL PRIMARY KEY 
  , name          TEXT       NOT NULL
  , pswhash       TEXT       NOT NULL
  , group_name    TEXT       NOT NULL CHECK (group_name IN ('GUEST', 'USER', 'ADMIN'))
  , UNIQUE        name
);
