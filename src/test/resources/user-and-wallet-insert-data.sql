INSERT INTO TB_WALLET (id, balance, creation_date, last_update_date)
VALUES
('d5f6e4a1-34c9-41f7-bb4a-1a31ff50e8a1', 1000, '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('f101d529-d8a5-4e6e-893c-6a7c11e0e787', 500, '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('7fe2db56-b655-4e79-adf0-e1f8e3125164', 0, '2024-04-11 12:00:00', '2024-04-11 12:00:00');

INSERT INTO TB_USER (id, name, document, email, password, user_type, wallet_id, creation_date, last_update_date)
VALUES
(10, 'Tony Stark', '11111111111', 'tonystark@email.com', 'tony123', 'COMMON', 'd5f6e4a1-34c9-41f7-bb4a-1a31ff50e8a1', '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
(15, 'Black Panther', '33333333333', 'blackpanther@email.com', 'balck123', 'STORE', 'f101d529-d8a5-4e6e-893c-6a7c11e0e787', '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
--user with insufficient balance
(20, 'Captain America', '7777777777', 'captainamerica@email.com', 'captain123', 'COMMON', '7fe2db56-b655-4e79-adf0-e1f8e3125164', '2024-04-11 12:00:00', '2024-04-11 12:00:00');