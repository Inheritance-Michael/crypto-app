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

## 🛠️ Tech Stack & Build Toolchain

| Component | Library / Framework | Version |
| :--- | :--- | :--- |
| **Language** | Kotlin | `2.2.10` |
| **Android Gradle Plugin** | AGP | `9.3.3` |
| **Build Tool** | Gradle | `9.5.0` |
| **Annotation Processing** | KSP (Kotlin Symbol Processing) | `2.2.10-2.0.2` |
| **Dependency Injection** | Dagger Hilt | `2.60.1` |
| **UI Framework** | Jetpack Compose & Material 3 | `2026.02.01` (BOM) |
| **Navigation** | AndroidX Navigation 3 | `1.2.1` |
| **Networking** | Retrofit 2 & Gson Converter | `2.11.0` |
| **Serialization** | Kotlinx Serialization | `1.6.3` |

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

## 🐞 Bug Tracker & In-Depth Post-Mortem

### The Runtime Exception
```text
java.lang.IllegalStateException: Given component holder class com.example.cryptoapp.MainActivity 
does not implement interface dagger.hilt.internal.GeneratedComponent or interface dagger.hilt.internal.GeneratedComponentManager
    at dagger.hilt.EntryPoints.get(EntryPoints.java:62)
    at dagger.hilt.android.internal.lifecycle.HiltViewModelFactory.createInternal(HiltViewModelFactory.java:207)
    at androidx.hilt.lifecycle.viewmodel.compose.HiltViewModelKt.rememberHiltViewModelFactory(HiltViewModel.kt:99)
    at com.example.cryptoapp.AppNavigationKt.AppNavigation(AppNavigation.kt:32)
```

### The Root Cause: Why adding `@AndroidEntryPoint` alone wasn't enough!
Many developers assume that simply adding `@AndroidEntryPoint` to `MainActivity.kt` fixes this error. However, if the app still crashes with the exact same error, it is because **the Hilt Gradle Plugin and KSP annotation processor were never configured in the Gradle build files**.

Without the **Hilt Gradle Plugin** (`id("com.google.dagger.hilt.android")`) and the **Hilt compiler** (`ksp("com.google.dagger:hilt-compiler:...")`):
1. Kotlin compiles `@AndroidEntryPoint` without errors, but **no code generation occurs**.
2. `Hilt_MainActivity.java` is never created.
3. Hilt's Bytecode Transformation task (`:app:transformDebugClassesWithAsm`) never runs to modify `MainActivity` to implement `GeneratedComponentManager`.
4. At runtime, `hiltViewModel()` checks whether `MainActivity` implements `GeneratedComponentManager`, finds that it does not, and crashes.

### The Complete Resolution
1. **Apply Hilt & KSP Plugins in Root `build.gradle.kts`:**
   ```kotlin
   plugins {
       id("com.google.dagger.hilt.android") version "2.60.1" apply false
       id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false
   }
   ```
2. **Apply Plugins & Compiler in `app/build.gradle.kts`:**
   ```kotlin
   plugins {
       id("com.google.dagger.hilt.android")
       id("com.google.devtools.ksp")
   }
   dependencies {
       implementation("com.google.dagger:hilt-android:2.60.1")
       ksp("com.google.dagger:hilt-compiler:2.60.1")
       implementation("androidx.hilt:hilt-navigation-compose:1.4.0")
   }
   ```
3. **Configure AGP 9 Compatibility in `gradle.properties`:**
   ```properties
   android.newDsl=false
   android.disallowKotlinSourceSets=false
   android.sync.suppressAgpWarnings=UNSUPPORTED_PROJECT_OPTION_USE
   ```
4. **Annotate Components:**
   - `MainActivity`: `@AndroidEntryPoint`
   - `CoinApplication`: `@HiltAndroidApp`
   - ViewModels: `@HiltViewModel` and `@Inject constructor`

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
