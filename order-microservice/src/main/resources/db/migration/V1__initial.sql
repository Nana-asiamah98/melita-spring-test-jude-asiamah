DROP
    EXTENSION if exists "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS tbl_role
(
    id        serial PRIMARY KEY,
    role_name VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS tbl_user
(
    id           uuid PRIMARY KEY default uuid_generate_v4() not null,
    account_name varchar(100),
    username     varchar(100) UNIQUE,
    password     varchar(100),
    role         varchar(50),
    created_at   TIMESTAMPTZ
)
