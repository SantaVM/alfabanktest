# alfabanktest

Тестовое задание.

*****************************
Описание задания:

Cервис, который обращается к сервису курсов валют, и отдает gif в ответ:
если рубль за последние сутки вырос относительно указанной валюты, то отдаем рандомную отсюда https://giphy.com/search/rich  
если упал - отсюда https://giphy.com/search/broke  
Ссылки:
REST API курсов валют - https://docs.openexchangerates.org/  
REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide  

Must Have
Сервис на Spring Boot 2 + Java / Kotlin
Запросы приходят на HTTP endpoint, туда передается код валюты
Для взаимодействия с внешними сервисами используется Feign
Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки.
На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
Для сборки должен использоваться Gradle.
Результатом выполнения должен быть репо на GitHub с инструкцией по запуску.

## Запуск

* С помощью Gradle

```
./gradlew bootRun
```

* С помощью Docker (предварительно создаем fat jar в папке ./build/libs c помощью задачи gradle bootJar)
```
docker build -t alfabanktest .
docker run -p 8080:8080 -t alfabanktest
```
После запуска приложения переходим на http://localhost:8080/api/{code}, где {code} это трехбуквенный код валюты, курс которой по отношению к рублю мы проверяем. 
По бесплатному тарифу openexchangerates базовая валюта для всех таблиц - только USD. 
Поэтому вычисляем кросс-курс валюта/USD/RUB.

После запроса получаем json-ответ вида:
```
{"searchString":"rich","url":"https://giphy.com/gifs/Originals-rich-pay-me-count-money-l0HlIvLpzz624GAUM"}
```
где поле "searchString" принимает одно из двух указанных в задании значений: "rich" или "broke"
а поле "url" содержит ссылку на найденную сервисом картинку.

Если курс за сутки не изменился, то ответ выглядит так:
```
{"searchString":"Rates unchanged!","url":"Rates unchanged!"}
```
