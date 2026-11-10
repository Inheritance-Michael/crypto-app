# 🪙 CryptoApp

A modern Android cryptocurrency tracking practice application built with **Kotlin**, **Jetpack Compose (Material 3)**, **Navigation 3**, and **Dagger Hilt** dependency injection.

This project consumes the public [CoinPaprika API](https://api.coinpaprika.com/) to display real-time cryptocurrency listings, details, team members, tags, and market status.

---

## 📱 Features

- **Coin Overview List:** Real-time list of cryptocurrencies with their rank, name, symbol, and active status.
- **Detailed Coin Information:** Detailed view for each coin featuring description, active/inactive badge, categorized tags, and team member directory.
- **Clean Architecture & MVVM:** Strict separation of concerns (Presentation, Domain, and Data layers).
- **Jetpack Compose Material 3:** Modern, responsive declarative UI design.
- **Modern Navigation 3:** Type-safe, backstack-aware navigation routing.
- **Reactive State Management:** Kotlin Coroutines and `StateFlow` delivering unidirectional data flow (UDF).
- **Dependency Injection:** Dagger Hilt providing singleton and viewmodel scoped dependencies.

---

## 🛠️ Tech Stack & Libraries

| Component | Library / Framework |
| :--- | :--- |
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose & Material 3 |
| **Dependency Injection** | Dagger Hilt (`hilt-android`, `hilt-navigation-compose`) |
| **Navigation** | AndroidX Navigation 3 (`navigation3-runtime`, `navigation3-ui`) |
| **Networking** | Retrofit 2 & Gson Converter |
| **Concurrency** | Kotlin Coroutines & Flow |
| **Serialization** | Kotlinx Serialization |

---

## 🏗️ Architecture Overview

The app follows Google's recommended Clean Architecture pattern:

```
app/src/main/java/com/example/cryptoapp/
├── data/
│   ├── remote/
│   │   ├── dto/                # Data Transfer Objects from CoinPaprika API
│   │   └── CoinPaprikaApi.kt   # Retrofit API interface
│   └── repository/             # Repository implementation
├── domain/
│   ├── model/                  # Pure Kotlin domain models (Coin, CoinDetail)
│   ├── repository/             # Repository abstractions / interfaces
│   └── use_case/               # Use cases (GetCoins, GetCoin)
├── presentation/
│   ├── coin_list/              # List screen composables, state & ViewModel
│   ├── coin_detail/            # Detail screen composables, state & ViewModel
│   ├── Screen.kt               # Type-safe navigation keys
│   └── ui/theme/               # Material 3 typography, color palette, theme
├── di/                         # Hilt dependency injection modules (AppModule)
├── CoinApplication.kt          # Application class annotated with @HiltAndroidApp
├── MainActivity.kt             # Single activity entry point with @AndroidEntryPoint
└── AppNavigation.kt            # Navigation 3 backstack and entry provider
```

---

## 🐞 Bug Tracker: Hilt Crash Post-Mortem

### The Error
```text
java.lang.IllegalStateException: Given component holder class com.example.cryptoapp.MainActivity 
does not implement interface dagger.hilt.internal.GeneratedComponent or interface dagger.hilt.internal.GeneratedComponentManager
    at dagger.hilt.EntryPoints.get(EntryPoints.java:62)
    at androidx.hilt.lifecycle.viewmodel.compose.HiltViewModelKt.rememberHiltViewModelFactory(HiltViewModel.kt:99)
```

### Why it happened:
When `hiltViewModel()` was invoked in Compose navigation (`AppNavigation.kt`), Hilt searched for an active component manager on the host `MainActivity`. Because `MainActivity` was missing `@AndroidEntryPoint`, Hilt's annotation processor did not generate the bytecode/component wrapper.

### The Fix:
1. Annotated `MainActivity` with `@AndroidEntryPoint`.
2. Created `CoinApplication` annotated with `@HiltAndroidApp` and registered it in `AndroidManifest.xml`.
3. Annotated ViewModels with `@HiltViewModel` and `@Inject constructor`.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug / Meerkat or newer
- JDK 17+ (or Android Studio bundled JBR 21)
- Android SDK 35+ / API 31+ device or emulator

### Installation & Run
1. Clone the repository:
   ```bash
   git clone https://github.com/Inheritance-Michael/crypto-app.git
   cd crypto-app
   ```
2. Open the project in Android Studio.
3. Sync Gradle and press **Run 'app'** (`Shift + F10`).

---

## 📄 License
This project was created as a hands-on learning and practice project. Open source under the [MIT License](LICENSE).
