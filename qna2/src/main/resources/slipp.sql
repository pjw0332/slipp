drop table if exists users;

create table users (
	userId 				varchar(12)	  	    not null,
	password 			varchar(12) 		not null,
	name 				varchar(20) 		not null,
	email 				varchar(50),	
	
	primary key (userId)
	);
	
	insert into users values('javajigi', 'password' , '자바지기', 'javajigi@slipp.net'); 