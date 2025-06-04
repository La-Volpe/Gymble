# Gymble

Gymble is an Android app built with Kotlin and Jetpack Compose that allows users to swipe through a deck of gyms inspired by characters from various games and franchises. Users can like or dislike gyms by swiping right or left and may be matched with a gym if luck is on their side.

## Architecture Overview

The project follows a simplified clean architecture style separating concerns into data, domain, and presentation layers.

- **Domain layer** contains business logic in plain Kotlin. `Gym`, `SwipeDirection`, and `SwipeResult` represent core domain models. Use case classes such as `GetGymsUseCase`, `ShuffleGymsUseCase`, and `SwipeGymUseCase` encapsulate actions.
- **Data layer** is responsible for retrieving gym data from the network. `GymsListApi` wraps a `HttpClient` from Ktor and returns `ApiResult` objects to signify success or failure. The API result is mapped to domain models in `GymMapper`.
- **Presentation layer** lives under `feature/matching` and `ui`. `MatchingViewModel` orchestrates user interactions by calling into the domain layer and exposes state flows consumed by Compose screens. Koin is used for dependency injection.

## Important Decisions & Trade-offs

- **Ktor Client** was chosen instead of Android's built-in networking libraries for its multiplatform support and coroutine-based API. This keeps the networking code simple and testable.
- **Koin for DI** offers lightweight dependency injection without requiring code generation. While Dagger/Hilt provide compile-time guarantees, Koin keeps build times short and configuration straightforward for this small project.
- **Compose-only UI**: The entire UI is written in Jetpack Compose. XML layouts were avoided to keep the UI layer concise and fully declarative.
- **Random Matching Logic**: Swiping right calls `SwipeGymUseCase`, which has a 10% chance to produce a match using `Random.nextInt`. This is intentionally simple to keep the example focused and avoid more complex backend state management.
- **Error Handling**: `GymsListApi` converts HTTP responses into `ApiResult` sealed classes. This design trades some boilerplate for explicit error mapping and easier testing of edge cases.
- **ViewModel Caching**: `MatchingViewModel` caches gyms in memory to allow restocking the swipe deck without fetching from the network again. While a persistent cache could improve resilience, the in-memory approach keeps the sample lightweight.

## How Things Work

1. **App Startup**
   - `GymbleApplication` starts Koin and loads modules defined in `AppModule.kt`.
   - Dependencies such as `HttpClient`, `GymsListApi`, and view models become available through Koin.
2. **Loading Gyms**
   - `MatchingViewModel.loadGyms()` calls `MatchingContext.loadGyms()`, which executes `GetGymsUseCase` to fetch gyms from the repository and then shuffles them.
   - On success, the UI state becomes `LoadedState` with a list of domain `Gym` objects.
3. **Swiping**
   - `SwipeableCardComponent` tracks drag offset and triggers `onSwiped` when the card passes a threshold.
   - The view model converts swipes into `SwipeDirection` and calls `SwipeGymUseCase` to determine if a match occurs.
   - A match displays `MatchedGymOverlay` using Compose animations; otherwise, the next card is shown.
4. **Network API**
   - `GymsListApi.fetchGymList()` performs an HTTP GET to a pastebin URL, decodes the JSON into `GymListDto`, and maps it to domain models via `GymMapper`.
   - Errors are mapped to `ApiResult.HttpError` or `ApiResult.NetworkError` to provide clear failure reasons.

## Running & Testing

This repository contains an Android project configured with Gradle. Run `./gradlew test` to execute unit tests and `./gradlew lintKotlin` for code style checks (requires internet access for dependencies).
