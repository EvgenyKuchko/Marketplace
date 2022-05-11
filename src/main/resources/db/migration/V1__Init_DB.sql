create table picture
(
    id          bigint not null auto_increment,
    creator     varchar(255) not null,
    description varchar(3000),
    file_path   varchar(255) not null,
    price       float  not null,
    user_id     bigint not null,
    primary key (id)
);
create table user
(
    id               bigint not null auto_increment,
    email            varchar(255) not null,
    nickname         varchar(255) not null,
    password         varchar(255) not null,
    user_description varchar(3000),
    primary key (id)
);
alter table picture
    add constraint picture_user_fk foreign key (user_id) references user