create table users (
  id                    bigserial,
  username              varchar(30) not null unique,
  password              varchar(80) not null,
  email                 varchar(50) unique,
  primary key (id)
);

create table roles (
  id                    serial,
  name                  varchar(50) not null,
  primary key (id)
);

CREATE TABLE users_roles (
  user_id               bigint not null,
  role_id               int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name)
values
('ROLE_USER')

insert into users (username, password, email)
values
('admin', '$2a$10$36VqC0u1DWcrx8uUMOadZeyJsFIkgeyHheEWzvMKZQjnFdBBDTu5G', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

# src/main/resources/data.sql
INSERT INTO product (name, price, category, image_path) VALUES
('Яблоки', 7990, 'Фрукты', '/catalog_img/apple.jpg'),
('Бананы', 8900, 'Фрукты', '/catalog_img/banana.jpg'),
('Ананасы', 8148, 'Фрукты', '/catalog_img/ananas.jpg'),
('Свекла', 25922, 'Овощи', '/catalog_img/svekkla.jpg'),
('Лук', 17500, 'Овощи', '/catalog_img/luk.jpg'),
('Чеснок', 575980, 'Овощи', '/catalog_img/garlic.jpg'),
('Молоко', 41453, 'Молочные', '/catalog_img/milk.jpg'),
('Йогурт', 40620, 'Молочные', '/catalog_img/yogurt.jpg'),
('Творог', 7990, 'Молочные', '/catalog_img/tvorog.jpg'),
('Печенье', 21650, 'К чаю', '/catalog_img/pechenki.jpg'),
('Пирожное', 3200, 'К чаю', '/catalog_img/pir.jpg'),
('Торт', 30758, 'К чаю', '/catalog_img/cake.jpg'),
('Пельмени', 9226, 'Замороженные', '/catalog_img/pelmen.jpg'),
('Оладьи', 14290, 'Замороженные', '/catalog_img/oladyi.jpg'),
('Овощной микс', 273060, 'Замороженные', '/catalog_img/veget.jpg');

select * from "public".users;