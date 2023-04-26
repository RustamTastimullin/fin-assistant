insert into simple_helper.roles(id, authorities, name)
    (VALUES (666, '666, 777, 10, 11', 'ADMIN'),
            (777, '777, 10, 11', 'SUPERUSER'),
            (10, '10', 'USER'),
            (11, '11, 10', 'CHIEF')
     )