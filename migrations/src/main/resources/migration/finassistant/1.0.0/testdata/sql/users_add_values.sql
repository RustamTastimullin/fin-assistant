insert into users(id, email, first_name, last_name, phone_number, is_active, password)
values (-10, 'test@test.ru', 'ADMIN', null, null, true,
        '$2a$12$9dytKJq7TKBriCO7MGhww.r7.0bos4EzJBkTB.kw11L8oUHv0gsoe'),
       (-20, 'test2@test.ru', 'SUPERUSER', null, null, true,
        '$2a$12$9dytKJq7TKBriCO7MGhww.r7.0bos4EzJBkTB.kw11L8oUHv0gsoe'),
       (-30, 'test3@test.ru', 'CHIEF', null, null, true,
        '$2a$12$9dytKJq7TKBriCO7MGhww.r7.0bos4EzJBkTB.kw11L8oUHv0gsoe'),
       (nextval('users_id_seq'), 'test4@test.ru', 'USER', null, null, true,
        '$2a$12$9dytKJq7TKBriCO7MGhww.r7.0bos4EzJBkTB.kw11L8oUHv0gsoe'),
       (nextval('users_id_seq'), 'test5@test.ru', 'USER2', null, null, true,
        '$2a$12$9dytKJq7TKBriCO7MGhww.r7.0bos4EzJBkTB.kw11L8oUHv0gsoe')