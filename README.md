# KuroLink Android Starter
Kotlin + Jetpack Compose Android project. Designed for Android 11+.

## Open and run
1. Install Android Studio and JDK 17.
2. Extract this ZIP and open the `KuroLink` folder in Android Studio.
3. Wait for Gradle sync, connect your Android phone with USB debugging enabled, then press Run.

## Included
- Dark/crimson Itachi-inspired branding
- Welcome, login and registration UI (email/phone selector)
- Home feed with sample story circles and posts
- Chats, Reels, Calls and Profile tabs

## Important
This is a runnable front-end prototype, **not yet a live social network**. Login/register are demo UI only; no OTP, accounts, messaging, video uploads, voice calls or video calls are connected.

## To make it live
Create a Firebase project for Android package `com.kurolink.app`, add `google-services.json`, enable Email/Password and Phone Authentication, then integrate Firestore, Storage and Firebase Cloud Messaging. Voice/video calling needs WebRTC plus signaling and usually a TURN relay. Phone OTP, video storage and TURN usage can incur charges. Add security rules, privacy policy, reporting/blocking and account deletion before public launch.
