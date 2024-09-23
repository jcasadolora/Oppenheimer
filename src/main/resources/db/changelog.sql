-- liquibase formatted sql

-- changeset jcasado:1727078411162-1
CREATE SEQUENCE phone_sequence START WITH 1 INCREMENT BY 1;

-- changeset jcasado:1727078411162-2
CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

-- changeset jcasado:1727078411162-3
CREATE TABLE phones
(
    id           BIGINT       NOT NULL,
    created      TIMESTAMP,
    modified     TIMESTAMP,
    xkey         VARCHAR(255) NOT NULL,
    number       BIGINT       NOT NULL,
    city_code    SMALLINT     NOT NULL,
    country_code SMALLINT     NOT NULL,
    user_id      BIGINT,
    CONSTRAINT pk_phones PRIMARY KEY (id)
);

-- changeset jcasado:1727078411162-4
CREATE TABLE users
(
    id       BIGINT       NOT NULL,
    created  TIMESTAMP,
    modified TIMESTAMP,
    xkey     VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset jcasado:1727078411162-5
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset jcasado:1727078411162-6
ALTER TABLE users
    ADD CONSTRAINT uc_users_token UNIQUE (token);

-- changeset jcasado:1727078411162-7
ALTER TABLE phones
    ADD CONSTRAINT FK_PHONES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

