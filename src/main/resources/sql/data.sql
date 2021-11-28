TRUNCATE TABLE role CASCADE;
INSERT INTO role(id, name)
VALUES
       (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

TRUNCATE TABLE usr CASCADE;
INSERT INTO usr(id, username, email, password, active)
VALUES
       (1, 'name1', 'name1@mail.ru', '$2a$10$.4WDUNcax9hKSaK7Oeug2eJj8kMnLpOoWb.eG.M4.mWEypUpOomCm', true),
       (2, 'name2', 'name2@mail.ru', '$2a$10$.4WDUNcax9hKSaK7Oeug2eJj8kMnLpOoWb.eG.M4.mWEypUpOomCm', true),
       (3, 'name3', 'name3@mail.ru', '$2a$10$.4WDUNcax9hKSaK7Oeug2eJj8kMnLpOoWb.eG.M4.mWEypUpOomCm', true);

TRUNCATE TABLE usr_role CASCADE;
INSERT INTO usr_role(usr_id, role_id)
VALUES
       (1, 1),
       (2, 1),
       (3, 2);


TRUNCATE TABLE review CASCADE;
INSERT INTO review(id, name, content, content_name, txt, score, rating, likes, images_link, user_id)
VALUES
       (1, 'Тестовое название 1', 'BOOK', 'Гарри Поттер 1', 'Текст обзора 1', 1, 1.3, 1, null, 1),
       (2, 'Тестовое название 2', 'MOVIE', 'Гарри Поттер 2', 'Текст обзора 2', 2, 2.3, 2, null, 2),
       (3, 'Тестовое название 3', 'GAME', 'Гарри Поттер 3', 'Текст обзора 3', 3, 3.3, 3, null, 3);
