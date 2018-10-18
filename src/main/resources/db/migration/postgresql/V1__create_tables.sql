create sequence user_id_seq start with 10 increment by 50;
create sequence role_id_seq start with 10 increment by 50;
create sequence txn_id_seq start with 10 increment by 50;

create table users (
    id bigint DEFAULT nextval('user_id_seq') not null,
    username varchar(255) not null CONSTRAINT user_username_unique UNIQUE,
    password varchar(255) not null,
    name varchar(255) not null,
    email varchar(255) not null CONSTRAINT user_email_unique UNIQUE,
    enabled boolean not null,
    last_password_reset_date timestamp,
    primary key (id)
);

create table roles (
    id bigint DEFAULT nextval('role_id_seq') not null,
    name varchar(255) not null CONSTRAINT role_name_unique UNIQUE,
    primary key (id)
);

create table user_role (
    user_id bigint REFERENCES users(id),
    role_id bigint REFERENCES roles(id)
);

create table transactions (
    id bigint DEFAULT nextval('txn_id_seq') not null,
    amount decimal(12,2) not null,
    type varchar(50) not null,
    created_on timestamp,
    user_id bigint REFERENCES users(id),
    primary key (id)
);
