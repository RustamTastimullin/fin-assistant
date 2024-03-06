## Запуск миграций с дефолтными настройками из liquibase.properties

```shell
# проверяем сгенеренный скрипт: лог в консоль и в target/liquibase/migrate.sql 
mvn -pl migrations process-resources liquibase:updateSQL@migrate-application-data "-Ddb.url=jdbc:postgresql://localhost:5432/fin_assistant?currentSchema=fin_assistant" "-Ddb.login=postgres" "-Ddb.password=postgres" "-DskipTests=true"
```

```shell
# запуск миграций
mvn -pl migrations process-resources liquibase:update@migrate-application-data "-Ddb.url=jdbc:postgresql://localhost:5432/fin_assistant?currentSchema=fin_assistant" "-Ddb.login=postgres" "-Ddb.password=postgres" "-DskipTests=true"
```