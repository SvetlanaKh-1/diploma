# Процедура запуска автотестов

***
До запуска автотестов необходимо иметь на компьютере или установить следующее ПО:
- браузер Google Chrome
- IntelliJ IDEA
- Docker Compose
- иметь учетную запись на GitHub

## Запуск автотестов:

1. Запустить контейнеризацию СУБД MySQl, PostgreSQL и Node.js командой в терминале:

`docker compose up`

2. Запустить SUT через терминал командами:

- для MySQl:

`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`

- для PostgreSQL:

`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`

3. Проверить в браузере, что приложение открывается по адресу: http://localhost:8080/


4. Запустить автотесты командами в терминале:

- для MySQl:

`./gradlew clean test`

- для PostgreSQL:

`./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`

*Примечание: перед запуском автотестов на разных СУБД необходимо останавливать приложение командой "Ctrl + C" в терминале и запускать приложение на нужной СУБД.*

5. Сформировать отчет по результатам тестов с помощью команды в терминале:

`./gradlew allureReport`

Сформированный отчет можно посмотреть в файле по адресу:

`diploma/build/reports/allure-report/allureReport/index.html`

или командой, которая сразу откроет сформированный отчет в браузере:

`./gradlew allureServe`