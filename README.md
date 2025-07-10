# Nutrition Tracker Android App

A comprehensive calorie counting and nutrition tracking Android application built with Kotlin, targeting API 24 (Android 7.0 Nougat).

## Features

### 🔐 User Authentication
- User registration and login system
- Secure local storage of user credentials
- Persistent login sessions
- Individual user profiles with personalized settings

### 📊 Calorie Tracking Dashboard
- Real-time calorie consumption tracking
- Visual display of daily calorie goals vs consumed calories
- Color-coded remaining calorie indicators
- Comprehensive food log with detailed entries

### 🍎 Food Entry Management
- Add food entries with custom names
- Specify calories per serving and number of servings
- Real-time calculation of total calories
- Delete food entries with confirmation
- View detailed food consumption history

### ⚙️ Personalization
- Set and update daily calorie goals
- User-specific data storage
- Customizable calorie targets per user

## Technical Specifications

### Platform Support
- **Minimum SDK**: API 24 (Android 7.0 Nougat)
- **Target SDK**: API 34
- **Language**: Kotlin
- **Architecture**: Modern Android Architecture with Repository pattern

### Database & Storage
- **Local Database**: Room SQLite database for offline functionality
- **User Data**: Stored locally with Room persistence
- **Food Entries**: Date-based organization for daily tracking
- **Shared Preferences**: For login state management

### UI/UX Design
- **Material Design 3**: Modern, clean interface
- **Responsive Layout**: Optimized for various screen sizes
- **Color Scheme**: Professional green and orange theme
- **Navigation**: Intuitive flow between screens

## App Architecture

### Database Schema
- **Users Table**: Username (PK), password, daily calorie goal
- **Food Entries Table**: ID (PK), username (FK), food details, calories, servings, date

### Core Components
1. **MainActivity**: Login and registration screen
2. **DashboardActivity**: Main calorie tracking interface
3. **AddFoodActivity**: Food entry creation screen
4. **Repository Pattern**: Clean data access layer
5. **Room Database**: Local data persistence

### Data Flow
```
UI Layer (Activities) ↔ Repository ↔ Room Database ↔ Local SQLite
```

## Installation & Setup

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin support
- Android SDK with API 24+ support

### Build Instructions
1. Clone or download the project
2. Open in Android Studio
3. Sync project with Gradle files
4. Build and run on device/emulator running Android 7.0+

### Dependencies
- AndroidX Core KTX
- Material Design Components
- Room Database
- Lifecycle Components
- RecyclerView
- Navigation Components

## Usage Guide

### Getting Started
1. **Registration**: Create a new account with username and password
2. **Login**: Access your personal nutrition dashboard
3. **Set Goals**: Configure your daily calorie target
4. **Track Food**: Add food entries throughout the day
5. **Monitor Progress**: View real-time calorie consumption

### Daily Workflow
1. Start the app (automatic login if previously logged in)
2. View today's calorie summary on dashboard
3. Add food items as you consume them
4. Monitor remaining calories for the day
5. Adjust calorie goals as needed

### Features Overview
- **Green indicators**: When within calorie goals
- **Red indicators**: When exceeding calorie limits
- **Real-time updates**: Immediate calculation of totals
- **Data persistence**: All data saved locally

## Security & Privacy
- All user data stored locally on device
- No external data transmission
- Secure password storage
- User data isolation
- Local backup support with privacy controls

## Compatibility
- **Android Version**: 7.0 Nougat (API 24) and above
- **Screen Sizes**: Phone and tablet compatible
- **Orientation**: Portrait optimized
- **Languages**: English (extensible for localization)

## Future Enhancements
- Food database integration
- Barcode scanning
- Nutritional information beyond calories
- Weekly/monthly reporting
- Export data functionality
- Cloud synchronization
- Social features

## Technical Notes
- Built with modern Android development practices
- Follows MVVM architecture principles
- Uses coroutines for asynchronous operations
- Implements proper lifecycle management
- Material Design 3 theming

This nutrition tracker provides a solid foundation for personal calorie management with room for expansion and additional features based on user needs.