drop database if exists teamtodo;
create database if not exists teamtodo;
use teamtodo;

create table if not exists user(
    id					bigint			auto_increment,
    email				varchar(50)		not null,
    encrypted_password	varchar(100)	not null,
    name				varchar(30)		not null,

    # constraints
    constraint pk_user primary key(id)
);

create table if not exists team(
    id		bigint		auto_increment,
    name	varchar(50)	not null,
    readme	text,

    # constraints
    constraint pk_team primary key(id)
);

create table if not exists team_assignment(
    id			bigint		auto_increment,
    user_id		bigint		not null,
    team_id		bigint		not null,

    # constraints
    constraint pk_team_assignment primary key(id),
    constraint fk_team_assignment_user_id_id_user foreign key(user_id) references user(id),
    constraint fk_team_assignment_team_id_id_team foreign key(team_id) references team(id)
);

create table if not exists todo(
    id			bigint			auto_increment,
    title		varchar(50)		not null,
    due			datetime		null,
    description	varchar(100)	null,
    team_id		bigint			not null,

    # constraints
    constraint pk_todo primary key(id),
    constraint fk_todo_team_id_id_team foreign key(team_id) references team(id)
);

create table if not exists todo_logging(
    id			bigint			auto_increment,
    status		varchar(20)		not null,
    timestamp	timestamp		not null,
    todo_id		bigint			not null,

    # constraints
    constraint pk_todo_logging primary key(id),
    constraint fk_todo_logging_todo_id_id_todo foreign key(id) references todo(id)
);
