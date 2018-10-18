create sequence user_id_seq start with 10 increment by 50;
create sequence role_id_seq start with 10 increment by 50;
create sequence txn_id_seq start with 10 increment by 50;

create table users (
    id bigint default user_id_seq.nextval,
    username varchar2(255) not null,
    password varchar2(255) not null,
    name varchar2(255) not null,
    email varchar2(255) not null,
    enabled boolean not null,
    last_password_reset_date timestamp,
    primary key (id),
    UNIQUE KEY user_username_unique (username),
    UNIQUE KEY user_email_unique (email)
);

create table roles (
    id bigint default role_id_seq.nextval,
    name varchar2(255) not null,
    primary key (id),
    UNIQUE KEY role_name_unique (name)
);

create table user_role (
    user_id bigint REFERENCES users(id),
    role_id bigint REFERENCES roles(id)
);

create table transactions (
    id bigint default txn_id_seq.nextval,
    amount decimal(12,2) not null,
    type varchar2(50) not null,
    created_on timestamp,
    user_id bigint REFERENCES users(id),
    primary key (id)
);
