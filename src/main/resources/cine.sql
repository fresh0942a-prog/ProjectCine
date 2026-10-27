DROP database if exists cine;
CREATE database cine;

CREATE user if not exists 'usuario_cine' identified by 'la_Clave';
Grant ALL privileges on cine.* to 'usuario_cine';

use cine;

Create table empresa (id_empresa INT auto_increment Primary Key, nombre varchar(100), identificador Varchar(50) unique, ubicacion varchar(100), activo boolean);
create table usuario (id_usuario int auto_increment primary key, nombre varchar(100), correo varchar(100) unique, contrasena varchar(50), rol varchar(20), activo boolean, id_empresa int,
  foreign key (id_empresa) references empresa(id_empresa));
create table pelicula (id_pelicula Int auto_increment primary key, titulo varchar(100), sinopsis text, genero varchar(30), clasificacion varchar(10), duracion int, ruta_imagen varchar(1024), activo boolean, id_empresa int,
  foreign key (id_empresa) references empresa(id_empresa));
create table sala (id_sala int auto_increment primary key, nombre varchar(50), filas int, asientos_por_fila int, activo boolean, id_empresa int,
  foreign key (id_empresa) references empresa(id_empresa));
create table funcion (id_funcion int auto_increment primary key, fecha_hora datetime, precio double, id_pelicula Int, id_sala int,
  foreign key (id_pelicula) references pelicula(id_pelicula), foreign key (id_sala) references sala(id_sala));
create table snack (id_snack int auto_increment primary key, nombre varchar(100), descripcion varchar(255), precio double, ruta_imagen varchar(1024), activo boolean, id_empresa int,
  foreign key (id_empresa) references empresa(id_empresa));
create table detalle_venta (id_detalle_venta int auto_increment primary key, id_venta int, fecha datetime, asiento varchar(5), cantidad int, precio double, id_usuario int, id_funcion int, id_snack int,
  unique (id_funcion, asiento),
  foreign key (id_usuario) references usuario(id_usuario), foreign key (id_funcion) references funcion(id_funcion), foreign key (id_snack) references snack(id_snack));







#datos para llenar las tablas (autogenerados, acuerdense de quitarlos, o hagan los suyos)

insert into empresa (nombre, identificador, ubicacion, activo) values
('Cine Central', 'cine-central', 'San José', true),
('Cines del Este', 'cines-del-este', 'Cartago', true),
('Cine Plaza Norte', 'plaza-norte', 'Heredia', true);

insert into usuario (nombre, correo, contrasena, rol, activo, id_empresa) values
('Administrador general', 'admin@cineapp.com', '123', 'ADMIN_GENERAL', true, null),
('Luis Rojas', 'luis@cine.com', '123', 'ADMIN', true, 1),
('Sofía Castro', 'sofia@cine.com', '123', 'ADMIN', true, 2),
('Diego Herrera', 'diego@cine.com', '123', 'ADMIN', true, 3),
('Ana Mora', 'ana@correo.com', '123', 'CLIENTE', true, null),
('José Solís', 'jose@correo.com', '123', 'CLIENTE', true, null);

insert into pelicula (titulo, sinopsis, genero, clasificacion, duracion, activo, id_empresa) values
('Jurassic Park', 'Un grupo de científicos visita un parque temático con dinosaurios.', 'Aventura', 'TP+12', 127, true, 1),
('Mi Pobre Angelito', 'Un niño se queda solo en casa y debe defenderla de dos ladrones.', 'Comedia', 'TP', 103, true, 1),
('Shrek 2', 'Shrek y Fiona viajan a conocer a los padres de ella.', 'Animación', 'TP', 93, true, 1),
('Jurassic Park', 'Un grupo de científicos visita un parque temático con dinosaurios.', 'Aventura', 'TP+12', 127, true, 2),
('Mi Pobre Angelito', 'Un niño se queda solo en casa y debe defenderla de dos ladrones.', 'Comedia', 'TP', 103, true, 3);

insert into sala (nombre, filas, asientos_por_fila, activo, id_empresa) values
('Sala 1', 5, 6, true, 1),
('Sala 2', 5, 6, true, 1),
('Sala 1', 5, 6, true, 2),
('Sala 1', 5, 6, true, 3);

insert into funcion (fecha_hora, precio, id_pelicula, id_sala) values
(curdate() + interval 1 day + interval 16 hour, 3500, 1, 2),
(curdate() + interval 1 day + interval 19 hour, 3000, 2, 1),
(curdate() - interval 3 day + interval 21 hour, 3000, 3, 2),
(curdate() + interval 1 day + interval 18 hour, 3200, 4, 3),
(curdate() + interval 1 day + interval 19 hour, 3000, 5, 4);

insert into snack (nombre, descripcion, precio, activo, id_empresa) values
('Palomitas grandes', 'Con mantequilla', 3000, true, 1),
('Refresco mediano', 'Vaso de 500 ml', 1500, true, 1),
('Palomitas grandes', 'Con mantequilla', 2800, true, 2),
('Combo nachos', 'Nachos y refresco', 4300, true, 3);

insert into detalle_venta (id_venta, fecha, asiento, cantidad, precio, id_usuario, id_funcion, id_snack) values
(1, now(), 'B3', 1, 3500, 5, 1, null),
(1, now(), 'B4', 1, 3500, 5, 1, null),
(1, now(), null, 1, 3000, 5, null, 1),
(2, now() - interval 4 day, 'C5', 1, 3000, 6, 3, null);