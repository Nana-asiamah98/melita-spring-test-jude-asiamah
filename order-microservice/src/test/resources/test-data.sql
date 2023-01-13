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
);

CREATE TABLE IF NOT EXISTS tbl_customer
(
    id         uuid PRIMARY KEY default uuid_generate_v4() not null,
    first_name varchar(100),
    last_name  varchar(100),
    email      varchar(100) UNIQUE,
    phone_number varchar(100) unique ,
    address    varchar(255),
    created_at TIMESTAMPTZ,
    updated_at timestamptz
);
CREATE TABLE IF NOT EXISTS tbl_products
(
    id           SERIAL PRIMARY KEY,
    product_name varchar(100) unique ,
    created_at   TIMESTAMPTZ,
    updated_at   timestamptz
);
CREATE TABLE IF NOT EXISTS tbl_product_variant
(
    id           SERIAL PRIMARY KEY,
    variant_name varchar(100) unique ,
    rate         numeric(10, 2),
    product_id   integer,
    created_at   TIMESTAMPTZ,
    updated_at   timestamptz,
    constraint fk_product foreign key (product_id) references tbl_products (id)
);

CREATE TABLE IF NOT EXISTS tbl_installation_address
(
    id         uuid PRIMARY KEY default uuid_generate_v4() not null,
    street_name varchar(100),
    town  varchar(100),
    region      varchar(100) UNIQUE,
    created_at TIMESTAMPTZ
);
CREATE TABLE IF NOT EXISTS tbl_order
(
    id           uuid PRIMARY KEY default uuid_generate_v4() not null,
    customer_detail_id uuid unique ,
    installation_detail_id uuid unique ,
    order_number varchar(100) unique ,
    notes text,
    total numeric(10,2),
    order_status varchar(50),
    created_at   TIMESTAMPTZ,
    updated_at   timestamptz
);
CREATE TABLE IF NOT EXISTS tbl_order_item
(
    id           uuid PRIMARY KEY default uuid_generate_v4() not null,
    package_name varchar(100),
    order_number   varchar(50),
    product_id   integer,
    product_variant_id   integer,
    amount numeric(10,2),
    constraint fk_order foreign key (order_number) references tbl_order (order_number),
    constraint fk_product foreign key (product_id) references tbl_products (id),
    constraint fk_product_variant foreign key (product_variant_id) references tbl_product_variant (id)

);

