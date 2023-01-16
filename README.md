Spring boot MVC demo project with CRUD operations.

Used:
Java 17,
Spring Boot 3,
Spring Security 6,
postgresql, thymeleaf, liquibase, lombok

Security with csrf token.
Supported CRUD operations with users. 

Goal:
- to make several helpful services

Known issues:
1) Любой пользователь может менять инфу любого пользователя
    - done
2) Добавить функцию удаления пользователя
    - done
3) Баг с затиранием ролей при редактировании профиля (ADMIN парсится не правильно)
    - done
4) Контроллер userEditForm (принимает юзера с его ролями и не правильно отображает вью, не видит админа)
    - done
5) Затирается пароль при обновлении пользователя?
    - done
6) Баг с затиранием ролей при редактировании профиля пользователем
    - done
7) После удаления разлогинить и переадресовать на /login

TODO list:
1) Добавить exceptions (для страниц и приложения)
2) Написать миграции
3) Изменить связи User - Role (для Role сделать таблицу id | name, связать c User по id через доп.таблицу)
4) Написать тесты
5) javadoc