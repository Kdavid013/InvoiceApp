INSERT INTO ROLES (name) VALUES ('USER');
INSERT INTO ROLES (name) VALUES ('ACCOUNTANT');
INSERT INTO ROLES (name) VALUES ('ADMIN');

INSERT INTO USERS (name, username, password) VALUES ('Admin', 'Admin', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');
INSERT INTO USERS (name, username, password) VALUES ('User', 'User', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');
INSERT INTO USERS (name, username, password) VALUES ('Accountant', 'Accountant', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM USERS u, ROLES r WHERE u.username = 'Admin' AND r.name = 'ADMIN';

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM USERS u, ROLES r WHERE u.username = 'User' AND r.name = 'USER';

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM USERS u, ROLES r WHERE u.username = 'Accountant' AND r.name = 'ACCOUNTANT'