# Playlist Maker

Playlist Maker - Android-приложение для поиска музыкальных треков через iTunes Search API, добавления треков в избранное и создания пользовательских плейлистов.

## Основные возможности

- Поиск треков через iTunes Search API.
- Отображение списка найденных треков с обложкой, исполнителем и длительностью.
- Экран детальной информации о треке.
- Добавление треков в избранное.
- Экран избранных треков.
- Создание плейлистов.
- Добавление треков в плейлисты.
- Просмотр экрана конкретного плейлиста.
- Добавление обложки плейлиста из галереи.
- Удаление трека из плейлиста по долгому нажатию.
- Хранение треков и плейлистов через Room.
- Хранение истории поиска через DataStore.

## Технологии

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel
- Coroutines
- Flow / StateFlow
- Retrofit
- Gson Converter
- Coil
- Room
- DataStore Preferences
- KSP

## Версии

- Android Studio: 2025.3.3 Patch 1
- Kotlin: 2.2.10
- Compile SDK: 36
- Min SDK: 29
- Target SDK: 36

## Сборка и запуск

1. Склонировать репозиторий:

```bash
git clone https://github.com/LightSmit/playlist-maker-android-RodionovMatvey.git 
```

2. Открыть проект в Android Studio.
3. Дождаться Gradle Sync.
4. Выбрать эмулятор Android-устройство.
5. Запустить приложение кнопкой Run.
