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
| **Serialization** | Kotlinx Serialization Plugin & JSON | `2.2.10` / `1.6.3` |
| **Networking** | Retrofit 2 & Gson Converter | `2.11.0` |

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

### Issue 1: Hilt `IllegalStateException` on `MainActivity`
```text
java.lang.IllegalStateException: Given component holder class com.example.cryptoapp.MainActivity 
does not implement interface dagger.hilt.internal.GeneratedComponent or interface dagger.hilt.internal.GeneratedComponentManager
```
- **Root Cause:** In Jetpack Compose, `hiltViewModel()` locates the host Activity and asserts that it implements `GeneratedComponentManager`.
- **The "Silent Compiler" Trap:** Simply annotating `MainActivity` with `@AndroidEntryPoint` is not enough if the **Hilt Gradle Plugin** (`id("com.google.dagger.hilt.android")`) and the **Hilt KSP compiler** (`ksp("com.google.dagger:hilt-compiler:...")`) are missing. Without them, no code generation or ASM bytecode transformation runs, leaving `MainActivity` as a plain `ComponentActivity`.
- **Resolution:**
  - Upgraded to Hilt `2.60.1` and KSP `2.2.10-2.0.2` compatible with AGP `9.3.3`.
  - Added compatibility options in `gradle.properties`:
    ```properties
    android.newDsl=false
    android.disallowKotlinSourceSets=false
    android.sync.suppressAgpWarnings=UNSUPPORTED_PROJECT_OPTION_USE
    ```
  - Created `CoinApplication` with `@HiltAndroidApp` and registered it in `AndroidManifest.xml`.

---

### Issue 2: `SerializationException: Serializer for class 'CoinListScreen' is not found`
```text
kotlinx.serialization.SerializationException: Serializer for class 'CoinListScreen' is not found.
Please ensure that class is marked as '@Serializable' and that the serialization compiler plugin is applied.
    at androidx.navigation3.runtime.serialization.NavKeySerializer.serialize(NavKeySerializer.android.kt:93)
    at androidx.navigation3.runtime.serialization.NavBackStackSerializer.serialize(NavBackStackSerializer.kt:55)
```
- **Root Cause:** AndroidX Navigation 3 saves and restores the navigation backstack (`rememberNavBackStack`) using `kotlinx.serialization`. While `Screen.kt` had `@Serializable` annotations, the **Kotlin Serialization Compiler Plugin** (`org.jetbrains.kotlin.plugin.serialization`) was not applied to the project.
- **The Trap:** Similar to Hilt, `@Serializable` requires the compiler plugin to generate the `$serializer` companion objects and serialization metadata. Without the plugin, `@Serializable` is a no-op marker.
- **Resolution:**
  - Registered plugin in `gradle/libs.versions.toml`:
    ```toml
    [plugins]
    kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
    ```
  - Declared in root `build.gradle.kts`:
    ```kotlin
    plugins {
        alias(libs.plugins.kotlin.serialization) apply false
    }
    ```
  - Applied in `app/build.gradle.kts`:
    ```kotlin
    plugins {
        alias(libs.plugins.kotlin.serialization)
    }
    ```

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

## 📄 Documentation
For the complete technical breakdown and architectural analysis, see the generated report:
📄 **[CryptoApp_Crash_Documentation.pdf](CryptoApp_Crash_Documentation.pdf)**

---

## 📄 License
This project was created as a hands-on learning and practice project. Open source under the [MIT License](LICENSE).
