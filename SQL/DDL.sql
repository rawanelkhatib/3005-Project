create table publisher
	(pub_id			numeric(8), 
	 pub_name		varchar(100), 
	 address		varchar(100),
	 email			varchar(100),
	 phone			varchar(20),
	 banking	 	varchar(100),
	 primary key (pub_id)
	);

create table book
	(isbn			varchar(15),
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
	 real_name		varchar(20), 
	 password		varchar(20),
	 shipping		varchar(100),
	 banking		varchar(100),
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
     	 isbn			varchar(15),
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


create or replace function getRevenueBookName(nameIN varchar (100))
returns decimal
as
$$
declare curr_price decimal;
declare rec RECORD;
begin
	curr_price = 0;
	for rec in (select price, num_sold, percentage from book natural join book_sales where upper(book_name) = upper(nameIN)) loop
			curr_price = curr_price + rec.num_sold*rec.price - rec.price*rec.percentage*.01;
	end loop;
	return curr_price;
end;
$$ language plpgsql;

create or replace function getRevenueGenre(genreIN varchar (100))
returns decimal
as
$$
declare curr_price decimal;
declare rec RECORD;
begin
	curr_price = 0;
	for rec in (select price, num_sold, percentage from book natural join book_sales where upper(genre) = upper(genreIN)) loop
			curr_price = curr_price + rec.num_sold*rec.price - rec.price*rec.percentage*.01;
	end loop;
	return curr_price;
end;
$$ language plpgsql;

create or replace function getRevenueAuthor(authorIN varchar (100))
returns decimal
as
$$
declare curr_price decimal;
declare rec RECORD;
begin
	curr_price = 0;
	for rec in (select price, num_sold, percentage from book natural join book_sales where upper(author) = upper(authorIN)) loop
			curr_price = curr_price + rec.num_sold*rec.price - rec.price*rec.percentage*.01;
	end loop;
	return curr_price;
end;
$$ language plpgsql;

create or replace function getRevenueISBN(isbnIN varchar (100))
returns decimal
as
$$
declare curr_price decimal;
declare rec RECORD;
begin
	curr_price = 0;
	for rec in (select price, num_sold, percentage from book natural join book_sales where upper(isbn) = upper(isbnIN)) loop
			curr_price = curr_price + rec.num_sold*rec.price - rec.price*rec.percentage*.01;
	end loop;
	return curr_price;
end;
$$ language plpgsql;

create or replace function getCostGenre(genreIN varchar (100))
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, num_sold from book natural join book_sales where upper(genre) = upper(genreIN)) loop
			curr_cost = curr_cost + rec.num_sold*rec.cost;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getCostAuthor(authorIN varchar (100))
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, num_sold from book natural join book_sales where upper(author) = upper(authorIN)) loop
			curr_cost = curr_cost + rec.num_sold*rec.cost;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getCostISBN(isbnIN varchar (100))
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, num_sold from book natural join book_sales where upper(isbn) = upper(isbnIN)) loop
			curr_cost = curr_cost + rec.num_sold*rec.cost;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getCostBookName(nameIN varchar (100))
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, num_sold from book natural join book_sales where upper(book_name) = upper(nameIN)) loop
			curr_cost = curr_cost + rec.num_sold*rec.cost;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getTotalCostinStock()
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, stock from book) loop
			curr_cost = curr_cost + rec.cost*rec.stock;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getTotalCostofSales()
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select cost, num_sold from book natural join book_sales) loop
			curr_cost = curr_cost + rec.cost*rec.num_sold;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;

create or replace function getCostToPublishers()
returns decimal
as
$$
declare curr_cost decimal;
declare rec RECORD;
begin
	curr_cost = 0;
	for rec in (select price, num_sold, percentage from book natural join book_sales) loop
			curr_cost = curr_cost + rec.num_sold*rec.price*rec.percentage*.01;
	end loop;
	return curr_cost;
end;
$$ language plpgsql;
