# Morky

An application to request and filter characters from
the [Rick and Morty](https://rickandmortyapi.com/) API.

## Architecture

This applications follows the MVVM + Clean Architecture with TDD.

## Compiling

### Requirements

- Android Studio Flamingo> or Java 17
- Android SDK 27 (min)
- Android SDK 33 (compile/target)

### Steps

1. Clone the project.
2. Import the project in Android Studio.
3. Sync dependencies
4. Compile with `gradlew build` or with the Run button in your desired device.

### Tests

Run `gradlew test` command.

## Dependencies

1. Compose: Reactive UI in Android
2. Landscapist with Fresco: Flexible image loading library for Compose. Fresco was chosen because
   it's has the 'click to reload' image feature by default.
3. Retrofit: Standard for making http requests on Android.
4. Moshi: Used with codegen to remove runtime overhead. Perfect with Kotlin and it's nullable types.
5. Room: Used for saving characters in an SQL database.
6. Hilt: Easy out of the box dependency injection
7. Coroutines + Flow: Background work with flows, who now has a powerful operators API.
8. Tests dependencies: mockk, truth, coroutiens-test.

Aside from Landscapist, Retrofit and Mosho, all dependencies come from either Google or Jetbrains.

## Contributing

I made this app for practice/professional purposes, but PR's are welcome!