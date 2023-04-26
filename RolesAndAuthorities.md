Иерархия ролей:

ADMIN (id:666, authorities:666, 777, 10, 11)
    -> SUPERUSER(id:777, authorities:777, 10, 11)
        -> CHIEF(id:11, authorities:11, 10)
            -> USER(id:10, authorities:10) - DEFAULT ROLE

Создать пользователя с правами ADMIN можно только из БД.
Присвоить роли от USER до SUPERUSER может только ADMIN или SUPERUSER.