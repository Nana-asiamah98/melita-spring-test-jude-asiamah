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
