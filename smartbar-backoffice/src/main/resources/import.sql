-- Categories
insert into category values (1000, 'Coffee', 'Coffee');
insert into category values (1001, 'Food', 'Food');
insert into category values (1002, 'Soft drinks', 'Soft drinks');

-- Articles
-- Coffee
insert into article (price, category_id, id, name, description, pictureBase64)
values (2.50, 1000, 2000, 'Espresso', 'Espresso', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (3.50, 1000, 2001, 'Latte', 'Latte', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (4.00, 1000, 2002, 'Iced coffee', 'Iced coffee', 'ABC');

-- Food
insert into article (price, category_id, id, name, description, pictureBase64)
values (3.00, 1001, 2003, 'Donuts', 'Donuts', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (3.80, 1001, 2004, 'Croissant', 'Croissant', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (4.00, 1001, 2005, 'Breakfast Sandwich', 'Breakfast Sandwich', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (6.00, 1001, 2006, 'pizza', 'pizza', 'ABC');

-- Softdrinks
insert into article (price, category_id, id, name, description, pictureBase64)
values (2.80, 1002, 2007, 'Coca-Cola Classic', 'Coca-Cola Classic', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (3.20, 1002, 2008, 'Mountain Dew', 'Mountain Dew', 'ABC');
insert into article (price, category_id, id, name, description, pictureBase64)
values (2.00, 1002, 2009, 'Water', 'Water', 'ABC');

-- Tables
insert into sbo_table values (true, 4, 3000, 'Oslo');
insert into sbo_table values (true, 8, 3001, 'Stockholm');
insert into sbo_table values (true, 12, 3002, 'New York');
insert into sbo_table values (false, 4, 3003, 'Munich');
insert into sbo_table values (true, 6, 3004, 'Barcelona');
