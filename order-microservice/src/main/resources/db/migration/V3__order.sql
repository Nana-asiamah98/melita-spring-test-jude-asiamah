CREATE TABLE IF NOT EXISTS tbl_installation_address
(
    id         uuid PRIMARY KEY default uuid_generate_v4() not null,
    street_name varchar(100),
    town  varchar(100),
    region      varchar(100),
    created_at TIMESTAMPTZ
);
CREATE TABLE IF NOT EXISTS tbl_order
(
    id           uuid PRIMARY KEY default uuid_generate_v4() not null,
    customer_detail_id uuid  ,
    installation_detail_id uuid  ,
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
    order_number   varchar(50),
    package_name   varchar(100),
    product_variant_name   varchar(100),
    amount numeric(10,2),
    constraint fk_order foreign key (order_number) references tbl_order (order_number)
);
