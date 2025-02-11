# 📖 BookStoreApp

**BookStoreApp** — это приложение для управления заказами книг, позволяющее пользователям просматривать каталог книг, оформлять заказы и отслеживать покупки. 

---

## 🛠️ Технологии

Проект разработан с использованием:
- **Kotlin** + **Jetpack Compose** для UI
- **MVVM** (Model-View-ViewModel) для разделения логики
- **Hilt** для внедрения зависимостей
- **Coroutines + Flow** для работы с асинхронными операциями
- **Firebase** для хранения данных
- **JUnit** для тестирования

---

## 📸 Скриншоты

*<img width="229" alt="image" src="https://github.com/user-attachments/assets/072d9d33-e32f-4040-901c-3b70a64baac7" />
*

---

## ⚙️ Установка

1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/your-username/BookStoreApp.git
   cd BookStoreApp
   ```

2. **Откройте проект в Android Studio**  
   Убедитесь, что у вас установлена последняя версия Android Studio.

3. **Запустите приложение**  
   Используйте эмулятор или реальное устройство для тестирования.

---

## 📂 Структура проекта

```plaintext
📂 app/
 ┣ 📂 data/               # Доступ к данным (API, БД)
 ┃ ┣ 📂 model/           # Модели данных
 ┃ ┣ 📂 repository/      # Репозитории
 ┃ ┗ 📂 datasource/      # Источники данных (локальные/удалённые)
 ┣ 📂 di/                 # Hilt модули
 ┣ 📂 ui/                 # Экраны и компоненты Jetpack Compose
 ┣ 📂 viewmodel/          # ViewModel
 ┗ 📂 utils/              # Утилитарные классы
```

---

## 🚀 Основные возможности

✔ Просмотр каталога книг  
✔ Оформление заказов  
✔ Управление личным кабинетом  
✔ История покупок  
✔ Поиск и фильтрация книг  

---

## 📝 TODO

- [ ] Реализовать уведомления о скидках  
- [ ] Улучшить дизайн интерфейса  

---

## 📌 Контрибьютинг

Хотите внести вклад? Добро пожаловать! Ознакомьтесь с [CONTRIBUTING.md](CONTRIBUTING.md) перед началом работы.

---

## 📜 Лицензия

Этот проект распространяется под лицензией MIT. Подробнее в [LICENSE](LICENSE).
