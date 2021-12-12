drop table sold_to;
drop table book_sales;
drop table sale;
drop table profile;
drop table book;
drop table publisher;

create table publisher
	(pub_id			numeric(8), 
	 pub_name		varchar(100), 
	 address		varchar(100),
	 email			varchar(100),
	 phone			varchar(11),
	 banking	 	varchar(100),
	 primary key (pub_id)
	);

create table book
	(isbn			varchar(13),
	 book_name		varchar(100),
	 author			varchar(100),
	 genre			varchar(100),
	 pages			numeric(4,0),
	 pub_id			numeric(8),
	 price			decimal(4,2),
	 cost			decimal(4,2),
	 stock			numeric(4,0),
	 percentage		numeric(2,0),
	 primary key (isbn),
	 foreign key (pub_id) references publisher(pub_id)
		on delete cascade
	);

create table profile
	(username		varchar(20), 
	 pf_name		varchar(20), 
	 password		varchar(20),
	 shipping		varchar(100),
	 owner			numeric(1) check (owner is not null),
	 primary key (username)
	);

create table sale
	(sale_id		numeric(8), 
	 date			date not null,
	 total			decimal(8,2),
	 shipping		varchar(100),
	 primary key (sale_id)
	);

create table book_sales
	(sale_id		numeric(8), 
     	 isbn			varchar(13),
	 num_sold		numeric(8),
	 primary key (sale_id, isbn),
	 foreign key (isbn) references book(isbn)
		on delete cascade
	);

create table sold_to
	(sale_id		numeric(8), 
     	username		varchar(20),
	 primary key (sale_id, username),
	 foreign key (sale_id) references sale(sale_id)
		on delete cascade,
	 foreign key (username) references profile(username)
		on delete cascade
	);

