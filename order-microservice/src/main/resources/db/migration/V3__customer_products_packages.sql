CREATE TABLE IF NOT EXISTS tbl_customer
(
    id uuid PRIMARY KEY default uuid_generate_v4
(
) not null ,
    first_name varchar
(
    100
),
    last_name varchar
(
    100
),
    email varchar
(
    100
) UNIQUE ,
    dob timestamp ,
    address varchar
(
    255
),
    created_at TIMESTAMPTZ,
    updated_at timestamptz
    );

CREATE TABLE IF NOT EXISTS tbl_products
(
    id uuid PRIMARY KEY default uuid_generate_v4
(
) not null ,
    product_name varchar
(
    100
),
    created_at TIMESTAMPTZ,
    updated_at timestamptz
    );

CREATE TABLE IF NOT EXISTS tbl_packages
(
    id uuid PRIMARY KEY default uuid_generate_v4
(
) not null ,
    package_name varchar
(
    100
),
    rate numeric
(
    10,
    2
),
    product_id uuid,
    created_at TIMESTAMPTZ,
    updated_at timestamptz,
    constraint fk_product foreign key
(
    product_id
) references tbl_products
(
    id
)
    );


