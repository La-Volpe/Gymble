# 🏋️ Gymble
**Gymble** is an Android app built with **Kotlin** and **Jetpack Compose** that lets users swipe through a deck of gyms inspired by characters from games and franchises. Like or dislike gyms by swiping right or left. You might even get matched if luck is on your side! 🎮✨


## 🏗️ Architecture Overview
The project follows a **simplified clean architecture**, separating concerns into **data**, **domain**, and **presentation** layers.

![](https://res.cloudinary.com/dlgtohpdw/image/upload/v1749222333/demo_sb6ym7.gif)

### 🔹 **Domain Layer**
- Contains **business logic** in plain Kotlin.
- Core domain models: `Gym`, `SwipeDirection`, and `SwipeResult`.
- Use cases:
  - `GetGymsUseCase` 📥
  - `ShuffleGymsUseCase` 🔀
  - `SwipeGymUseCase` 👆👇

### 📡 **Data Layer**
- Handles **network operations** to fetch gym data.
- `GymsListApi` uses **Ktor’s `HttpClient`** to retrieve data and returns `ApiResult` (success/failure).
- `GymMapper` converts API responses into domain models.

### 🎨 **Presentation Layer**
- Located under `feature/matching` and `ui`.
- `MatchingViewModel` manages state and user interactions.
- **Koin** is used for **dependency injection** (lightweight & easy to configure).
- **Jetpack Compose** powers the entire UI! 🚀


## ⚖️ Important Decisions & Trade-offs

| Decision | Reason | Trade-off |
|----------|--------|-----------|
| **Ktor Client** 🌐 | Multiplatform support & coroutine-friendly API | Slightly more setup than Retrofit or okhttp |
| **Koin for DI** 🔌 | Lightweight, no code generation | Less compile-time safety than Dagger/Hilt (But does it matter anyway?) |
| **Compose-only UI** 🎨 | Fully declarative & concise | beginner devs might find it hard to adapt to Reactive programming. |
| **Random Matching Logic** 🎲 | Simple 10% match chance | Not realistic for production |
| **Error Handling** ⚠️ | `ApiResult` sealed classes for explicit error mapping | Slightly more boilerplate |
| **ViewModel Caching** 🧠 | In-memory gym storage for quick restocking | No persistence (loses data on app restart) |


## ⚙️ How Things Work

### 1️⃣ **App Startup**
- `GymbleApplication` initializes **Koin** and loads modules from `AppModule.kt`.
- Dependencies (`HttpClient`, `GymsListApi`, and ViewModels) become available.

### 2️⃣ **Loading Gyms**
- `MatchingViewModel.loadGyms()` fetches gyms via `GetGymsUseCase`.
- On success, UI state updates to `LoadedState` with a shuffled list of `Gym` objects.

### 3️⃣ **Swiping**
- `SwipeableCardComponent` tracks drag gestures.
- Swipes trigger `onSwiped` → `SwipeGymUseCase` determines if a match occurs (10% chance! 🍀).
- **Match?** → `MatchedGymOverlay` with animations! 🎉
- **No match?** → Next card appears.

### 4️⃣ **Network API**
- `GymsListApi.fetchGymList()` makes an HTTP GET request.
- Decodes JSON into `GymListDto` → maps to domain models via `GymMapper`.
- Errors return as `ApiResult.HttpError` or `ApiResult.NetworkError`.


## 🚀 Running & Testing
- **Run tests:** `./gradlew test`
- **Lint checks:** `./gradlew lintKotlin` (requires internet for dependencies)
