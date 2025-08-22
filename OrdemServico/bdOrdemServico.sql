create database dbOs2025;
use dbos2025;

create table tbusuarios(
	iduser int primary key,
    usuario varchar(15) not null,
    fone varchar(15),
    login varchar(15) not null unique,
    senha varchar(250) not null,
    perfil varchar(20) not null
);

select * from tbusuarios;

describe tbusuarios;

insert into tbusuarios (iduser, usuario, fone, login, senha, perfil)
values(1,"miimizinha","66999326633", "miimizinha", "123456", "Admim");

create table tbclientes(
	idcli int primary key auto_increment,
    nomecli varchar(50) not null,
    endcli varchar(100),
    fonecli varchar(15) not null,
    emailcli varchar(50) unique
);    

select * from tbclientes;

insert into tbclientes (nomecli, endcli,fonecli,  emailcli)
values("Iasmim Lino", "Buriti", "66-9999-9999", "iasmim.souza@gmail.com"); 

create table tbos(
	os int primary key auto_increment,
    data_os timestamp default current_timestamp,
    tipo varchar(15) not null,
    situacao varchar(20) not null,
    equipamento varchar(150) not null,
    defeito varchar(150),
    servico varchar (150),
    tecnico varchar (150),
    valor decimal(10, 2),
    idcli int not null,
    foreign key(idcli) references tbclientes(idcli)
);    
