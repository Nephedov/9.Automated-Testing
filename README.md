# «SQL»

## Решения
### Задание 1
 * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/docker-compose.yml">docker-compose.yml</a>. - с MySQL DB.
 * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/AuthTest.java">AuthTest.java</a>. - класс с автотестами.
 * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/schema/schema.sql">schema.sql</a>. - описание схемы DB.

## Что было сделано
  * Настроен <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/build.gradle">build.gradle</a> с зависимостями:
    * JunitJupier.
    * Lombok.
    * Faker.
    * Selenide.
    * MySQL.
  * Реализован <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/docker-compose.yml">docker-compose.yml</a> с MySQL DB.
  * Реализован <a href="https://github.com/Nephedov/9.Automated-Testing/tree/main/src/test/java/page">репозиторий</a> с классами описывающими элементы страниц приложения и методы взаимодействия с ними:
    * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/page/DashboardPage.java">DashboardPage.java</a>,
    * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/page/LoginPage.java">LoginPage.java</a>,
    * <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/page/VerificationPage.java">VerificationPage.java</a>.
  * Реализован класс генерации тестовых данных <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/data/DataGenerator.java">DataGenerator.java</a>.
  * Реализовак класс с методами взаимодействия с базой данных <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/data/MySqlHelper.java">MySqlHelper.java</a>.
  * Реализован класс с <a href="https://github.com/Nephedov/9.Automated-Testing/blob/main/src/test/java/AuthTest.java">автотестами</a> входа в систему.

## Описание Задания №1: скоро дедлайн

Разработчикам особо не до вас, им ведь нужно пилить новые фичи, поэтому они подготовили сборку, работающую с СУБД, и даже приложили схему базы данных (см. файл [schema.sql](schema.sql)). Но при этом сказали: «Остальное вам нужно сделать самим, там несложно» 😈

Что вам нужно сделать:
1. Внимательно изучить схему.
1. Создать Docker container на базе MySQL 8, прописать создание базы данных, пользователя, пароля.
1. Запустить SUT ([app-deadline.jar](app-deadline.jar)). Для указания параметров подключения к базе данных можно использовать:
- либо переменные окружения `DB_URL`, `DB_USER`, `DB_PASS`;
- либо указать их через флаги командной строки при запуске: `-P:jdbc.url=...`, `-P:jdbc.user=...`, `-P:jdbc.password=...`. Внимание: при запуске флаги не нужно указывать через запятую. Приложение не использует файл `application.properties` в качестве конфигурации, конфигурационный файл находится внутри JAR-архива;
- либо можете схитрить и попробовать подобрать значения, зашитые в саму SUT.

Внимательно посмотрите, как и куда сохраняются коды генерации в СУБД, и напишите тест, который, взяв информацию из БД о сгенерированном коде, позволит вам протестировать вход в систему через веб-интерфейс.

P.S. Неплохо бы ещё проверить, что при трёхкратном неверном вводе пароля система блокируется.

Итого в результате у вас должно получиться:
* docker-compose.yml*,
* app-deadline.jar,
* schema.sql,
* проект Gradle c кодом ваших автотестов.

