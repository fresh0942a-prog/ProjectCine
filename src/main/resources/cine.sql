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
