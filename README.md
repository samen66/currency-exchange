# currency-exchange

## Предварительные требования

Перед тем, как начать, убедитесь, что у вас установлены:
- Docker
- Docker Compose
- Maven (если необходимо собирать проект из исходных кодов)
- Git (для скачивания последних изменений из репозитория)

## Установка и запуск

Следуйте этим шагам для запуска проекта:

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/samen66/currency-exchange.git
   cd currency-exchange
   ```

2. Для запуска проекта выполните скрипт `start.sh`:
    ```bash
    ./start.sh
   ```
### Для остановки проекта и очистки ресурсов выполните скрипт `stop.sh`:
   ```bash 
      ./stop.sh
   ```
## Документация API для работы с лимитами

Этот документ содержит подробные инструкции по использованию API для работы с лимитами, которое позволяет клиентам управлять данными о лимитах, связанных с операциями с валютой.

### Базовый URL

API доступно по адресу:
[http://localhost:8080/api/limits](http://localhost:8080/api/limits)

Убедитесь, что ваш сервер приложений запущен и доступен.

### Конечные точки

#### 1. Получить все лимиты

**Запрос:**

- **Метод:** `GET`
- **Endpoint:** `/all`
- **Описание:** Возвращает список всех установленных лимитов.

**Пример запроса:**
GET http://localhost:8080/api/limits/all

**Пример ответа:**

```json
[
    {
        "limit_sum": 1000.00,
        "limit_remains": 500.00,
        "limit_datetime": "2024-08-12T05:36:01Z",
        "limit_currency_shortname": "USD"
    },
    {
        "limit_sum": 500.00,
        "limit_remains": 0.00,
        "limit_datetime": "2024-08-12T05:36:10Z",
        "limit_currency_shortname": "USD"
    }
]
```

#### 2. Получить все лимиты
**Запрос:**

- **Метод:** `POST`
- **Endpoint:** `/`
- **Описание:** Создает новую запись лимита.
- **Тело:** JSON-объект, указывающий детали нового лимита.

**Пример запроса:**
POST http://localhost:8080/api/limits
Content-Type: application/json

**Пример ответа:**

```json
{
    "limit_sum": 500,
    "limit_currency_shortname": "USD"
}
```