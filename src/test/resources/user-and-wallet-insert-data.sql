INSERT INTO TB_WALLET (id, balance, creation_date, last_update_date)
VALUES
('d5f6e4a1-34c9-41f7-bb4a-1a31ff50e8a1', 1000, '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('f101d529-d8a5-4e6e-893c-6a7c11e0e787', 500, '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('7fe2db56-b655-4e79-adf0-e1f8e3125164', 0, '2024-04-11 12:00:00', '2024-04-11 12:00:00');

INSERT INTO TB_USER (id, name, document, email, password, user_type, wallet_id, creation_date, last_update_date)
VALUES
('1f1c42f3-74ef-4a18-84a3-f681cf56d226', 'Tony Stark', '11111111111', 'tonystark@email.com', 'tony123', 'COMMON', 'd5f6e4a1-34c9-41f7-bb4a-1a31ff50e8a1', '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('4cf6933b-b11c-4e0c-b775-89fd24f1ec09', 'Black Panther', '33333333333', 'blackpanther@email.com', 'balck123', 'STORE', 'f101d529-d8a5-4e6e-893c-6a7c11e0e787', '2024-04-11 12:00:00', '2024-04-11 12:00:00'),
('5a0e940a-4915-4460-8512-8efa8f166a7d', 'Captain America', '7777777777', 'captainamerica@email.com', 'captain123', 'COMMON', '7fe2db56-b655-4e79-adf0-e1f8e3125164', '2024-04-11 12:00:00', '2024-04-11 12:00:00');