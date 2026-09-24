# Movie Sort App

Консольное Java-приложение для заполнения и сортировки массива объектов
`Movie` (фильм). Демонстрирует паттерны **Strategy** и **Builder**,
собственную (не библиотечную) реализацию сортировки и защищённую от
падений обработку пользовательского ввода.

## Возможности

- Бесконечный цикл работы программы; выход — только через пункт меню `0`.
- Три способа заполнения массива: случайная генерация, ручной ввод, чтение
  из файла (формат строки: `название;год;жанр`).
- Пользователь сам указывает длину массива.
- Сортировка по любому из трёх полей `Movie`: название, год выпуска, жанр —
  по возрастанию или по убыванию.
- Алгоритм сортировки — **собственная реализация сортировки слиянием**
  (`MovieSorter`), без использования `Collections.sort()`, `List.sort()`,
  `Arrays.sort()` или иных готовых реализаций.
- Класс `Movie` создаётся только через `Builder`, валидация всех полей
  происходит на этапе `build()`.
- В `Movie` явно реализованы `equals()`, `hashCode()`, `toString()`.
- Любой некорректный ввод (в том числе строки в файле) обрабатывается через
  `ValidationException` и не приводит к падению программы — на верхнем
  уровне (`Main.run()`) дополнительно стоит "рубеж защиты" от непредвиденных
  `RuntimeException`.

## Структура проекта

```
src/main/java/com/sortingapp/
├── Main.java                       — точка входа, меню, главный цикл
├── model/
│   └── Movie.java                  — модель с Builder, equals/hashCode/toString
├── strategy/
│   ├── SortStrategy.java           — интерфейс стратегии сравнения
│   ├── TitleSortStrategy.java      — сортировка по названию
│   ├── YearSortStrategy.java       — сортировка по году
│   ├── GenreSortStrategy.java      — сортировка по жанру
│   └── MovieSorter.java            — контекст Strategy + своя сортировка слиянием
├── fill/
│   ├── FillStrategy.java           — интерфейс стратегии заполнения
│   ├── RandomFillStrategy.java     — случайное заполнение
│   ├── ManualFillStrategy.java     — ручной ввод с консоли
│   └── FileFillStrategy.java       — чтение из файла
└── validation/
    ├── MovieValidator.java         — правила валидации полей
    └── ValidationException.java    — checked-исключение валидации
```

## Сборка и запуск

С Maven:

```bash
mvn clean package
java -jar target/movie-sort-app.jar
```

Без Maven:

```bash
find src -name "*.java" > sources.txt
javac -d out -encoding UTF-8 @sources.txt
java -cp out com.sortingapp.Main
```

## Формат файла данных

Каждая строка — один фильм, поля через `;`:

```
Интерстеллар;2014;Фантастика
```

Пример с намеренно некорректными строками — `movies-sample.txt` в корне
проекта (строки с пустым названием, нечисловым или недопустимым годом будут
пропущены с предупреждением, программа продолжит работу).

## Git: ветки и слияние в main

Требование — число веток не меньше числа участников команды, все ветки в
итоге смерджены в `main`. Пример для 3 участников:

```bash
git init
git checkout -b main
git add .
git commit -m "Initial project structure"

# участник 1: модель + валидация
git checkout -b feature/model-and-validation
# ... работа над model/Movie.java, validation/*
git add . && git commit -m "Add Movie model with Builder and validation"

# участник 2: стратегии сортировки
git checkout main
git checkout -b feature/sort-strategies
# ... работа над strategy/*
git add . && git commit -m "Add sort strategies and MovieSorter"

# участник 3: заполнение массива + главный класс
git checkout main
git checkout -b feature/fill-and-main
# ... работа над fill/*, Main.java
git add . && git commit -m "Add fill strategies and Main application loop"

# слияние всех веток в main
git checkout main
git merge --no-ff feature/model-and-validation
git merge --no-ff feature/sort-strategies
git merge --no-ff feature/fill-and-main

# публикация
git remote add origin <URL вашего репозитория на GitHub/GitLab>
git push -u origin main
```

Если участников больше или меньше — добавьте/уберите ветки по тому же
принципу (например, отдельную ветку под README/CI), правило "ветки ≥
участников" от этого не пострадает.
