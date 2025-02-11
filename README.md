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

<img width="229" alt="image" src="https://github.com/user-attachments/assets/072d9d33-e32f-4040-901c-3b70a64baac7" />
<img width="230" alt="image" src="https://github.com/user-attachments/assets/d320686a-3606-49ae-b999-148dbe9f9d10" />
<img width="242" alt="image" src="https://github.com/user-attachments/assets/44c074b0-be1f-4a88-b22d-756140a1e34d" />
<img width="232" alt="image" src="https://github.com/user-attachments/assets/e2b0137d-5f21-4097-bc44-d475de5034c2" />
<img width="251" alt="image" src="https://github.com/user-attachments/assets/7fd5eab9-0eeb-42e3-b45e-7bda4978a4c9" />
<img width="243" alt="image" src="https://github.com/user-attachments/assets/a1c396ce-6410-453b-a97c-f3413ad267fb" />
<img width="261" alt="image" src="https://github.com/user-attachments/assets/e521afaf-5683-44b3-bf3d-f36ea28745f8" />
<img width="264" alt="image" src="https://github.com/user-attachments/assets/cbe19f3f-3d58-4588-a1a9-6fb99ecd70cb" />
---

## ⚙️ Установка

1. **Клонируйте репозиторий**:
   ```bash
   git clone https://github.com/MamedovEm1l/BookStore.git
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
