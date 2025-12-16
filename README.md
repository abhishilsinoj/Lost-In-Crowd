# Mobile App Project — LostInCrowd

## Overview
**Lost In Crowd** is a complete redesign and expansion of the **Concert Ticket App** starter lab. While the original lab provided basic functionality,This project transforms it into a modern concert and festivals booking application.Its is Built entirely using modern Jetpack Compose,The app addresses the common frustration of a generic interfaces by providing a fluid, animated, and user-centric experience.
The app addresses the need for a modern, responsive booking experience. It enhances the original concept by introducing:
> * **Complex State Management:** Replaced simple counters with a robust MVVM architecture using `StateFlow`.
> * **Modern UI Interface:** While the original version of this app was just a simple list.This redesign fixes that by making the app look modern, fun, and easy to use.
> * **Realistic Simulation:** Added asynchronous background processing (multithreading) to simulate real-world network latency during ticket purchases.

## AI assistance
This project is my own original work. I utilized AI tools (e.g., chatgpt) selectively to assist with specific code syntax and development commands. All AI-generated content was critically reviewed, tested, and executed manually.
AI assistance was specifically used for the following areas:
> * In designing the Coroutine scope and simulated network delay logic to prevent UI freezing.
> * In retrieving the specific Kotlin syntax/commands needed to implement some of my concept,like simulating  ticket availability using random function.
> * In identifying commands to resolve Gradle namespace errors.
> * In configuring NavHost to correctly pass event IDs between screens.

## Features
> * The app has a fresh look with a Spotlight section that shows featured events,It also has 2 other sections which show the upcoming concerts and major festivals.
> * A Visual feedback which shows Availability of seats.Besides that you can choose how many tickets to buy,and the price updates automatically.
> * A dedicated screen where users can view and delete purchased tickets.
> * **Theme Personalization**:
  - **Day Mode**: White Lavender & Purple theme
  - **Night Mode**: Neon Cyan & Magenta theme
> * Implemented background processing,When you click "Buy," the app waits 2 seconds simulating a network connection.
> * Added a "Help" screen to guide first-time users.

Provide a clear, concise description of your app._  
Explain its purpose, key features, and what problem it solves or what value it adds.

---

## Features

_Example list the main features of your app._

- [ ] Redesigned user interface using Jetpack Compose
- [ ] Added new feature: [e.g. persistent storage / custom timer / multi-screen navigation]
- [ ] Multithreading for [e.g. data saving / API requests / heavy computations]
- [ ] Help/instruction screens for first-time users
- [ ] Clean architecture (e.g., MVVM, separation of concerns)

---

## Architecture & Design

_Explain how your app is structured. Include diagrams or code snippets if helpful._

- Example below:
  - **Architecture pattern:** (e.g., MVVM)
  - **Key components:**
      - `MainScreen.kt` – UI entry point
      - `ViewModel` – Handles business logic and state
      - `Repository` – Data access layer
      - `DataStore/Room` – Persistent storage
  - **Concurrency:** Describe how you handled background tasks (e.g., `viewModelScope`, `WorkManager`)

**Diagram (optional but recommended)**  
Insert or link to a diagram (e.g., using [mermaid](https://mermaid.live/) or image).

---

## UI / UX Design

_Describe your UI design choices._

- Color scheme: [describe or link design inspiration]
- Layout responsiveness: [e.g., supports portrait and landscape]
- Accessibility: [e.g., contrast ratio, touch target size, TalkBack support]
- Compose features used: [e.g., LazyColumn, Scaffold, Navigation]

_Screenshots (recommended):_

| Home Screen | Help Screen |
|-------------|-------------|
| ![Home](screenshots/home.png) | ![Help](screenshots/help.png) |

---

## How to Build & Run

_Provide clear instructions for building and running your app._

1. Clone this repository: `git clone git@github.com:uniofgreenwich/ELEE1146_CW_<GITHUBUSERNAME>`
2. Open the project in **Android Studio Giraffe or newer**.
3. Connect a device or launch an emulator.
4. Click **Run**.

---

## Technologies Used

_List key technologies, frameworks, and libraries._

- Kotlin
- Jetpack Compose
- Coroutines
- [Any other libraries: e.g., Room, DataStore, WorkManager]

---

## Multithreading / Background Tasks

_Explain what background processing your app performs and how you implemented it._

Example:
> The app uses `viewModelScope` coroutines to persist data asynchronously, ensuring the UI remains responsive. A `WorkManager` task handles exporting data in the background.

---

## References

_List API documentation, design guidelines, or libraries you relied on._

_Example_

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Other references or tutorials]

**Acknowledge third-party code or libraries used (including license if required).**

---

## Licence

[Educational Community License 2.0 [ECL]](./LICENSE.md)

## Author 

[YOUR-GITHUB-USERNAME](https://github.com/YOUR-GITHUB-USERNAME)

---

## Future Improvements

_Reflect on what could be added or improved if you had more time._

- [ ] [Feature idea 1]
- [ ] [Feature idea 2]
