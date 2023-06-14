delete from Pizza;
delete from Pizza_Order;
delete from Ingredient;
delete from Role_User;
delete from Users;
delete from Users_Authorities;

insert into Ingredient (id, name, type) values ('SIZE25', '25 cm', 'SIZE');
insert into Ingredient (id, name, type) values ('SIZE32', '32 cm', 'SIZE');
insert into Ingredient (id, name, type) values ('SIZE40', '40 cm', 'SIZE');

insert into Ingredient (id, name, type) values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into Ingredient (id, name, type) values ('BEEF', 'Beef', 'PROTEIN');
insert into Ingredient (id, name, type) values ('CHKN', 'Chicken', 'PROTEIN');
insert into Ingredient (id, name, type) values ('SAUG', 'Sausage', 'PROTEIN');

insert into Ingredient (id, name, type) values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into Ingredient (id, name, type) values ('LETC', 'Lettuce', 'VEGGIES');
insert into Ingredient (id, name, type) values ('SWPE', 'Sweet Pepper', 'VEGGIES');

insert into Ingredient (id, name, type) values ('CHED', 'Cheddar', 'CHEESE');
insert into Ingredient (id, name, type) values ('JACK', 'Monterrey Jack', 'CHEESE');

insert into Ingredient (id, name, type) values ('SLSA', 'Salsa', 'SAUCE');
insert into Ingredient (id, name, type) values ('TMSC', 'Tomato Sauce', 'SAUCE');
insert into Ingredient (id, name, type) values ('BBQ', 'BBQ', 'SAUCE');


-- Create admin account (password - admin)
insert into Role_User (id, rolename) values (0, 'ROLE_ADMIN');
insert into Users (id, username, password, fullname, phone_number) values (2, 'admin', '$2a$10$v.s1TtjRt9rbl1.NduoOmeOgMBtyTLFd4CqhRj9HCKDpMdToyDKjO', 'admin', '123');
insert into Users_Authorities (user_id, role_id) values (2, 0);

-- Create user account (password - user)
insert into Role_User (id, rolename) values (1, 'ROLE_USER');
insert into Users (id, username, password, fullname, phone_number) values (-1, 'user', '$2a$10$rUvsQ4IVlZekge9fBCwXHOfD3mKPwrfARgb7NfIR7pRMLHfGRkvHm', 'user', '123');
insert into Users_Authorities (user_id, role_id) values (-1, 1);