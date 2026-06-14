# DemoQA UI autotests

Учебный проект UI-автоматизации, организованный по принципам рабочего тестового проекта.

## Стек

- Java 17
- Maven
- JUnit 5
- Selenide
- Allure
- Checkstyle

## Структура

```text
src/test/java/config  - конфигурация запуска
src/test/java/data    - тестовые данные
src/test/java/pages   - Page Object
src/test/java/tests   - тестовые сценарии
```

## Запуск

Для Windows:

```powershell
.\mvnw.cmd clean test
```

Для Linux и macOS:

```bash
./mvnw clean test
```

Запуск отдельных наборов:

```powershell
.\mvnw.cmd clean test -Psmoke
.\mvnw.cmd clean test -Pregression
.\mvnw.cmd clean test -Pnegative
```

Полная проверка проекта, включая Checkstyle:

```powershell
.\mvnw.cmd clean verify
```

## Параметры окружения

Параметры можно передавать как Java system properties:

```powershell
.\mvnw.cmd clean test -Dheadless=true -Dbrowser=chrome -Dtimeout=15000
```

Поддерживаемые параметры:

| Параметр | Значение по умолчанию |
|---|---|
| `baseUrl` | `https://demoqa.com` |
| `browser` | `chrome` |
| `browserSize` | `1920x1080` |
| `timeout` | `10000` |
| `headless` | `false` |
| `remoteUrl` | не задан |

Те же параметры можно задать переменными окружения: `BASE_URL`, `BROWSER`, `BROWSER_SIZE`,
`TIMEOUT`, `HEADLESS`, `REMOTE_URL`.

Пример запуска через Selenium Grid:

```powershell
.\mvnw.cmd clean test -DremoteUrl=http://localhost:4444/wd/hub -Dheadless=true
```

## Allure

Результаты создаются в `target/allure-results`. Для просмотра отчёта требуется Allure CLI:

```powershell
allure serve target/allure-results
```

При падении теста в Allure сохраняются screenshot и исходный HTML страницы.

## CI

GitHub Actions запускает smoke-тесты для pull request и полный `verify` для основной ветки.
Allure results сохраняются как артефакт workflow.
