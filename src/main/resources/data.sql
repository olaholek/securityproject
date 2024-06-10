insert into app_users(username, password, first_name, last_name) values('admin', '$2a$12$L/UUPBMxen2bQ67M6G7c0up9pX.xk5qYJpE3IyoqIDfhWPMn.hcOe', 'Jan', 'Kowalski');
insert into app_users(username, password, first_name, last_name) values('user', '$2a$12$L/UUPBMxen2bQ67M6G7c0up9pX.xk5qYJpE3IyoqIDfhWPMn.hcOe', 'Adam', 'Nowak');

insert into app_roles(name) values('ROLE_ADMIN');
insert into app_roles(name) values('ROLE_USER');

insert into user_roles(user_id, role_id) values(1, 1);
insert into user_roles(user_id, role_id) values(2, 2);