create table users
(
    id        serial primary key,
    tg_id     varchar(12) not null,
    full_name varchar(60) not null,
    phone     varchar(13),
    role      varchar(10) default 'CUSTOMER',
    address   varchar(255),
    constraint tg_uq
        unique (tg_id)
);

create table positions
(
    id          serial primary key,
    p_name      varchar(40) not null,
    category    varchar(30) not null,
    description varchar(250),
    price       float default 0,
    weight      varchar(30),
    image       varchar(50),
    constraint p_name_uq
        unique (p_name)
);

create table orders
(
    id             serial primary key,
    order_owner_id varchar(36) not null,
    supplier_id    varchar(36),
    delivery_id    varchar(36),
    notes          varchar(200),
    amount         float      default 0,
    payback_from   float      default 0,
    payment_code   varchar(6),
--     status         smallint   default 100,
    payment_type   varchar(4) default 'CARD',
    delivery_type  varchar(8) default 'DELIVERY',
    positions      jsonb       not null,
    delivery_info  jsonb       not null,
    order_logs     jsonb       not null,
    constraint ooid_fk
        foreign key (order_owner_id) references users (tg_id),
    constraint sid_fk
        foreign key (supplier_id) references users (tg_id),
    constraint did_fk
        foreign key (delivery_id) references users (tg_id)
);

create table resource_params
(
    id    serial primary key,
    key   varchar(15) not null,
    value varchar(40) not null,
    constraint key_uq
        unique (key)
);