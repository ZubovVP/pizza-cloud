create table if not exists Ingredient (
    id varchar(6) primary key not null,
    name varchar(25) not null,
    type varchar(10) not null
    );

create table if not exists Users (
    id identity primary key,
    username varchar(50) not null,
    password varchar(100) not null,
    fullname varchar(50) not null,
    street varchar(50),
    city varchar(50),
    st varchar(50),
    zip varchar(50),
    phone_number varchar(50) not null
    );

create table if not exists Pizza_Order (
    id identity primary key,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state varchar(5) not null,
    delivery_zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null,
    user_id INT references Users(id)
    );

create table if not exists Pizza (
    id identity primary key,
    name varchar(50) not null,
    created_at timestamp not null,
    pizza_order_id INT references Pizza_Order(id)
    );

create table if not exists Role_User (
    id INT primary key,
    rolename varchar(50) not null
    );

create table if not exists INGREDIENT_PIZZA(
    ingredients_id varchar(6) references Ingredient(id),
    pizza_id INT references Pizza(id)
    );

create table if not exists Users_Authorities(
    user_id INT references Users(id),
    role_id INT references Role_User(id)
    );



