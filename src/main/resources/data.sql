INSERT INTO ROLES (id, name) VALUES (1, 'USER');
INSERT INTO ROLES (id, name) VALUES (2, 'ACCOUNTANT');
INSERT INTO ROLES (id, name) VALUES (3, 'ADMIN');

INSERT INTO USERS (id, name, username, password) VALUES (1, 'Admin', 'Admin', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');
INSERT INTO users_roles (user_id, role_id ) VALUES (1,3);
INSERT INTO USERS (id, name, username, password) VALUES (2, 'User', 'User', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');
INSERT INTO users_roles (user_id, role_id ) VALUES (2,1);
INSERT INTO USERS (id, name, username, password) VALUES (3, 'Accountant', 'Accountant', '$2a$10$F9ePXZBKb4IsJRoqZZthPOOm5Swz59dHAFmhqPdff.lcrmZhcaDwa');
INSERT INTO users_roles (user_id, role_id ) VALUES (3,2);