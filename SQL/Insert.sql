insert into publisher 
values (1, 'Atkins Publishing Co.', '256 Yellow Brick Road, CA, USA', 'publishing@apc.com', '19097654532', 'Account number: 181625, Tansit Number: 778');

insert into book values 
('9780812936827', 'Modern Chess Openings', 'Nick de Firmian', 'Games', 869, 1, 40.99, 20.45, 17, 8);

insert into book values
('3600843936511', 'Lord of the Flies', 'william Golding', 'Fiction', 342, 1, 32.99, 15.45, 19, 5);

insert into profile values 
('brandon', 'Brandon', '1234', '990 Dew drop road, sudbry on, p3g1l2', 'Account Number: 373633, Transit Number: 009, Branch Number: 909090', 1);

insert into profile values 
('rawan', 'Rawan', '1234', 'The Big Pyramid, Egypt', 'Account Number: 373633, Transit Number: 009, Branch Number: 909090' 0);

insert into publisher values
(3, 'HaperCollins', '195 Broadway New York, NY 10007 USA', 'orders@harpercollins.com', '8002427737', 'Account number: 438490, Transit Number: 456' );

insert into book
values('9780061233845', 'The Alchemist', 'Paulo Coelho', 'Fiction', 167, 3, 11.99, 5.99, 15, 10);

insert into publisher
values(4, 'AUCPress', '113 Kasr el Aini Street, Egypt', 'sue.ostfield@aucegypt.edu', '0227976926', 'Account number: 812547, Transit Number: 972' );

insert into book
values('9780552995825', 'Sugar Street', 'Naguib Mahfouz', 'Fiction', 308, 4, 8.12, 4.45, 10, 15);

insert into book
values('9780307947109', 'Palace Walk', 'Naguib Mahfouz', 'Fiction', 501, 4, 9.32, 5.45, 10, 11);

insert into book
values('9780062060624', 'The Song of Achilles', 'Paulo Coelho', 'Fiction', 416, 3, 13.99, 7.34, 15, 3);

insert into book
values('9780062457714', 'The Subtle Art of Not Giving a F*ck', 'Mark Manson', 'Self-help', 224, 3, 24.99, 12.13, 17, 12);

insert into publisher
values(2, 'Bloomsbury', '50 Bedford Square, London, WC1B 3DP', 'contact@bloomsbury.com', '2076315600', 'Account number: 394571, Transit Number: 123' );

insert into book
values('9781526626585', 'Harry Potter and the Philosophers Stone', 'J.K Rowling', 'Fantasy', 223, 2, 15.04, 6.87, 20, 6);

insert into book
values('9781338716535', 'Harry Potter and the Chamber of Secrets', 'J.K Rowling', 'Fantasy', 251, 2, 16.03, 5.78, 18, 7);

insert into publisher
values(5, 'The Crown Publishing Group', '222 Rosewood Drive, Danvers, MA', 'customerservice@prh.com', '9787508400', 'Account number: 123456, Transit Number: 391' );

insert into book
values('9780345805461', 'Gone Girl', 'Gillian Flynn', 'Mystery', 432, 5, 14.99, 8.12, 14, 9);

insert into sale
values(1, '2021-12-01', 64.01, '4196 Desert Broom Court');

insert into book_sales
values(1, '9780061233845', 2);

insert into book_sales
values(1, '9780062457714', 1);

insert into book_sales
values(1, '9781526626585', 1);

insert into sold_to
values(1, 'rawan');

insert into sale
values(2, '2021-12-01', 178.98, '3758 Buena Vista Avenue');

insert into book_sales
values(2, '9780812936827', 2);

insert into book_sales
values(2, '3600843936511', 2);

insert into book_sales
values(2, '9780345805461', 1);

insert into book_sales
values(2, '9781338716535', 1);

insert into sold_to
values(2, 'rawan');
