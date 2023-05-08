INSERT INTO users (id, name, email, password, working_hours)
VALUES (1, 'User_First', 'user@gmail.com', crypt('password', gen_salt('bf', 8)), 7),
       (2, 'Admin', 'admin@gmail.com', crypt('admin', gen_salt('bf', 8)), 12),
       (3, 'User_Second', 'user2@gmail.com', crypt('password1', gen_salt('bf', 8)), 16);

INSERT INTO user_roles (user_id, role)
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'USER');

INSERT INTO lunch (id, start_time, end_time, date, user_id)
VALUES (1, '12:00:00', '12:20:00', CURRENT_DATE, 1),
       (2, '12:20:00', '12:40:00', CURRENT_DATE, 1),
       (3, '17:00:00', '17:20:00', CURRENT_DATE, 2);
