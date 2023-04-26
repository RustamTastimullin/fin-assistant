## Запуск миграций с дефолтными настройками из liquibase.properties

```shell
# проверяем сгенеренный скрипт: лог в консоль и в target/liquibase/migrate.sql 
mvn process-resources liquibase:updateSQL@simple-helper "-Ddb.url=jdbc:postgresql://localhost:5432/simple_helper?currentSchema=simple_helper" "-Ddb.login=postgres" "-Ddb.password=postgres" "-DskipTests=true"
```

```shell
# запуск миграций
mvn process-resources liquibase:update@simple-helper "-Ddb.url=jdbc:postgresql://localhost:5432/simple_helper?currentSchema=simple_helper" "-Ddb.login=postgres" "-Ddb.password=postgres" "-DskipTests=true"
```