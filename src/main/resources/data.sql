-- Populate rooms
INSERT INTO rooms (id, room_type, capacity, price, discount_price, is_available) VALUES (1, 'PREMIUM', 2, 150.00, 150*0.9, true);
INSERT INTO rooms (id, room_type, capacity, price, discount_price, is_available) VALUES (2, 'PRESIDENTIAL', 3, 300.00, 300*0.9, true);
INSERT INTO rooms (id, room_type, capacity, price, discount_price, is_available) VALUES (3, 'DELUX 1', 2, 100.00, 100*0.9, true);
INSERT INTO rooms (id, room_type, capacity, price, discount_price, is_available) VALUES (4, 'DELUX 2', 2, 100.00, 100*0.9, true);
INSERT INTO rooms (id, room_type, capacity, price, discount_price, is_available) VALUES (5, 'ECONOMIC', 1, 50.00, 50*0.9, true);


INSERT INTO user (id, name, surname, username, password, street, city, state, zipcode, phone, email ,type)
VALUES (1, 'John', 'Doe', 'john', '{bcrypt}$2a$10$EIjIv1.Ek8j3tkBzCJHYIet62rU7J3w07YRRJHilBGO8QZPf1Wz.m', '123 Main St', 'Los Angeles', 'CA', '90001', '555-123-4567', 'john@example.com', 'guest');


INSERT INTO user (id, name, surname, username, password, street, city, state, zipcode, phone, email ,type)
VALUES (2, 'starwood', 'Doe', 'starwood', '{bcrypt}$2a$10$EIjIv1.Ek8j3tkBzCJHYIet62rU7J3w07YRRJHilBGO8QZPf1Wz.m', '123 Main St', 'Los Angeles', 'CA', '90001', '555-123-4568', 'starwood@example.com', 'starwood');

