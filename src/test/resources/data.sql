insert into customers(id, email, password)
values (100, 'test@test.com', '$2a$10$LpGxGOvSYlO3yHgIjO.gPeye20FtjAzSNzfoHsOvBBa6N2MDY./MW');

insert into orders(id, total_amount, customer_id, date)
values (100, 2.0, 100, '2022-01-15');
insert into orders(id, total_amount, customer_id, date)
values (101, 8.0, 100, '2022-02-15');
insert into orders(id, total_amount, customer_id, date)
values (102, 14.0, 100, '2022-02-25');

insert into books(id, name, author, amount, stock_count)
values (100, 'book name', 'author', 1.0, 10);
insert into books(id, name, author, amount, stock_count)
values (101, 'book name1', 'author1', 2.0, 20);
insert into books(id, name, author, amount, stock_count)
values (102, 'book name2', 'author2', 3.0, 30);

insert into basket_items(id, amount, count, book_id, order_id)
values (100, 2.0, 2, 100, 100);
insert into basket_items(id, amount, count, book_id, order_id)
values (101, 2.0, 2, 100, 101);
insert into basket_items(id, amount, count, book_id, order_id)
values (102, 6.0, 3, 101, 101);
insert into basket_items(id, amount, count, book_id, order_id)
values (103, 2.0, 2, 100, 102);
insert into basket_items(id, amount, count, book_id, order_id)
values (104, 6.0, 3, 101, 102);
insert into basket_items(id, amount, count, book_id, order_id)
values (105, 6.0, 2, 102, 102);