CREATE TABLE user
(
    user_id      BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'user id',
    username     VARCHAR(250) NOT NULL COMMENT 'username',
    password     VARCHAR(100) NOT NULL COMMENT 'password',
    first_name   VARCHAR(100) NOT NULL COMMENT 'first name',
    last_name    VARCHAR(100) NOT NULL COMMENT 'last name',
    role         VARCHAR(100) NOT NULL COMMENT 'access role',
    created_date datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (user_id),
    KEY user_idx1 (username)
)
    COMMENT 'Test user table to handle authentication and authorization for the application';
