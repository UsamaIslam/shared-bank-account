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