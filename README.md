
# E-Commerce App

This project is an Android e-commerce application built using **Kotlin** and **Jetpack Compose**. It features user authentication, a home screen with categorized products, a wishlist, a shopping cart, and user profile functionalities.

## Tech Stack
- **Kotlin**: Main programming language for the application.
- **Jetpack Compose**: For building UI components.
- **MVVM Architecture**: Organizes the codebase into ViewModel, Repository, and UI layers.
- **Firebase**: Used for authentication and real-time database functionality.
- **Retrofit**: For handling API requests.
- **Hilt**: For dependency injection.
- **Coil**: For loading images.

## Navigation Graph
The app uses Jetpack Compose's NavHostController for handling navigation. The navigation graph includes the following routes:

- **AuthGraph**: Handles user authentication with routes for login, signup, and password recovery.
- **HomeNavGraph**: Manages the navigation between the home, wishlist, cart, and profile screens.
- **DetailsNavGraph**: Handles navigation for detailed product views.

## Project Structure
- `ui.home`: Contains the screens related to the home, such as `BaseHomeScreen`, `BottomBar`, and various composables for showing product details, categories, and more.
- `navigation`: Manages navigation between different screens using **NavGraph**.
- `viewmodels`: Includes the ViewModel classes for managing UI logic.
- `repositories`: Handles data fetching and business logic.

## Dependencies
This project uses the following dependencies:
- **Jetpack Compose**
- **Hilt**
- **Firebase Authentication**
- **Retrofit**
- **Coil**

Refer to the `build.gradle` and `gradle.properties` files for the full list of dependencies.

## UI Design Reference
The UI components for this project were inspired by the following Figma design:

[Laza E-commerce Mobile App UI Kit](https://www.figma.com/community/file/1245385141730558466/laza-ecommerce-mobile-app-ui-kit)


## Installation and Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/madenyasin/ecommerce-app.git
   ```
2. Open the project in Android Studio.
    ### Firebase Setup [Authentication(Email/Password, Google), RTDB]
    To run this project, you need to set up Firebase. Follow the steps below:
    
    1. Download the `google-services.json` file from the [Firebase Console](https://console.firebase.google.com/), after adding your Android application. Place this file in the `app` directory of your project.
       
    2. Create a `keys.properties` file in the root directory of the project and add the following line:
       ```properties
       WEB_CLIENT_ID = "your_web_client_id"

3. Build the project and run it on an emulator or a physical device.

## Screenshots

<img src="./ss/1.jpg" alt="Screenshot" width="200"/> <img src="./ss/2.jpg" alt="Screenshot" width="200"/>
<img src="./ss/3.jpg" alt="Screenshot" width="200"/>
<img src="./ss/4.jpg" alt="Screenshot" width="200"/>
<img src="./ss/5.jpg" alt="Screenshot" width="200"/>
<img src="./ss/6.jpg" alt="Screenshot" width="200"/>