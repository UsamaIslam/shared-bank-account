drop table if exists authorities cascade;
drop table if exists users cascade;
drop table if exists users_authorities cascade;
CREATE TABLE authorities
(
    id        INT AUTO_INCREMENT NOT NULL,
    authority VARCHAR(255),
    CONSTRAINT pk_authorities PRIMARY KEY (id)
);

ALTER TABLE authorities
    ADD CONSTRAINT uc_authorities_authority UNIQUE (authority);

CREATE TABLE users
(
    id                      INT AUTO_INCREMENT NOT NULL,
    username                VARCHAR(255),
    password                VARCHAR(255),
    account_non_expired     BOOLEAN,
    account_non_locked      BOOLEAN,
    credentials_non_expired BOOLEAN,
    enabled                 BOOLEAN,
    first_name              VARCHAR(255),
    last_name               VARCHAR(255),
    email_address           VARCHAR(255),
    birthdate               date,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_authorities
(
    authorities_id INT NOT NULL,
    users_id       INT NOT NULL,
    CONSTRAINT pk_users_authorities PRIMARY KEY (authorities_id, users_id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE users_authorities
    ADD CONSTRAINT fk_useaut_on_authorities FOREIGN KEY (authorities_id) REFERENCES authorities (id);

ALTER TABLE users_authorities
    ADD CONSTRAINT fk_useaut_on_users FOREIGN KEY (users_id) REFERENCES users (id);

insert into authorities(authority) VALUES ( 'ROLE_USER' );
insert into authorities(authority) VALUES ( 'ROLE_ADMIN' );
insert into authorities(authority) VALUES ( 'ROLE_BANKER' );

INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ( 'user', '$2a$12$z0TyNiXX75DBE4t7.Dh2B.iZOwT.MlAd5dT7dgkLIIxL9NPXGZj/W',true,true,true,true,'a','b','a@gmail.com', '1996-12-03' );

INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ( 'admin', '$2a$12$z0TyNiXX75DBE4t7.Dh2B.iZOwT.MlAd5dT7dgkLIIxL9NPXGZj/W',true,true,true,true,'c','d','c@gmail.com', '1996-12-03' );

INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate)
VALUES ( 'banker', '$2a$12$z0TyNiXX75DBE4t7.Dh2B.iZOwT.MlAd5dT7dgkLIIxL9NPXGZj/W',true,true,true,true,'e','f','e@gmail.com', '1996-12-03' );

INSERT INTO users_authorities(authorities_id, users_id) VALUES ( 1, 1);
INSERT INTO users_authorities(authorities_id, users_id) VALUES ( 2, 2);
INSERT INTO users_authorities(authorities_id, users_id) VALUES ( 3, 3);