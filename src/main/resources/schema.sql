CREATE TABLE if not exists app_users
(
    user_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255)       NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
);

CREATE TABLE if not exists app_roles
(
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE if not exists user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES app_users (user_id),
    FOREIGN KEY (role_id) REFERENCES app_roles (role_id)
);

CREATE TABLE if not exists permissions (
                             permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE if not exists role_permissions (
                                 role_id BIGINT NOT NULL,
                                 permission_id BIGINT NOT NULL,
                                 PRIMARY KEY (role_id, permission_id),
                                 FOREIGN KEY (role_id) REFERENCES app_roles(role_id),
                                 FOREIGN KEY (permission_id) REFERENCES permissions(permission_id)
);

