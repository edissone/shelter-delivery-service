create table users
(
    tg_id     varchar(12) primary key not null,
    full_name varchar(60)             not null,
    phone     varchar(13),
    role      varchar(10) default 'CUSTOMER',
        constraint tg_id_uq
            unique (tg_id)
);

create table positions
(
    id          serial primary key,
    p_name      varchar(30) not null,
    category    varchar(15) not null,
    description varchar(200),
    price       float default 0,
    weight      varchar(20),
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
    status         smallint   default 100,
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
        foreign key (delivery_id_id) references users (tg_id)
);

-- CREATED -- 100, CONFIRMED, 200, PREPARING 210 , GOING, 300, DELIVERED, 400