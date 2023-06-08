delete from Pizza;
delete from Pizza_Order;
delete from Ingredient;
delete from Role_User;

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

insert into Role_User (id, rolename) values (1, 'ROLE_USER"');
insert into Role_User (id, rolename) values (0, 'ROLE_ADMIN"');