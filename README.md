# Android Techniques — Firebase Admin

Android app demonstrating Firebase Cloud Messaging (FCM) and basic CRUD
against a NestJS backend that uses the Firebase Admin SDK.

- Android app: https://github.com/hebertcisco/android-techniques-firebase-admin
- NestJS Firebase Admin library: https://github.com/hebertcisco/nestjs-firebase-admin

Optional companion backend example (recommended for local testing):
- NestJS example app: https://github.com/hebertcisco/nestjs-techniques-firebase-admin

## What This Shows

- FCM push notifications handled via `FirebaseMessagingService`.
- User list CRUD via REST using Retrofit + OkHttp.
- Simple notification UI (system notification + in‑app dialog).
- Configurable backend URL via `local.properties`.

## Prerequisites

- Android Studio (Giraffe or newer), Java 8+ toolchain.
- A Firebase project with FCM enabled.
- A running backend exposing the expected endpoints (NestJS recommended).

## Project Structure (high‑level)

- `app/src/main/java/com/example/nestjs_firebase_admin/service/CustomFirebaseMessagingService.java`
  - Receives FCM messages, shows a notification and a dialog.
- `app/src/main/java/com/example/nestjs_firebase_admin/api/*`
  - Retrofit client and API interface for CRUD operations.
- `app/src/main/java/com/example/nestjs_firebase_admin/*`
  - Activities, adapter, and models used by the sample UI.
- `app/build.gradle`
  - Declares Firebase Messaging, Retrofit, and reads `API_BASE_URL`.

## Backend Options

You can use any backend that provides:

- FCM send capability via Firebase Admin SDK.
- REST endpoints used by this app:
  - `GET /` → list users
  - `POST /` → create user
  - `PUT /update/:id` → update user
  - `DELETE /delete/:id` → delete user

Recommended:

- Library: `nestjs-firebase-admin`
  - Repo: https://github.com/hebertcisco/nestjs-firebase-admin
- Example backend implementing the above endpoints:
  - Repo: https://github.com/hebertcisco/nestjs-techniques-firebase-admin

### Quick start (backend example)

1) Clone the example repo and install deps

   - `git clone https://github.com/hebertcisco/nestjs-techniques-firebase-admin`
   - `cd nestjs-techniques-firebase-admin && npm i`

2) Configure Firebase Admin service account

   - Follow the library README to provide credentials (env or JSON path).

3) Run the server

   - `npm run start:dev` (defaults to `http://localhost:3000`)

## Android Setup

1) Add Firebase to the app

- In the Firebase Console, add an Android app for your package `com.example.nestjs_firebase_admin`.
- Download `google-services.json` and place it into `app/google-services.json`.
- Ensure the Google Services plugin is applied (already present):
  - `app/build.gradle` has `id 'com.google.gms.google-services'`.

2) Point the app to your backend

- Set `API_BASE_URL` in `local.properties` at the project root:

  ```properties
  API_BASE_URL=http://10.0.2.2:3000/
  ```

  Notes:
  - Use `http://10.0.2.2` for Android Emulator to reach host `localhost`.
  - The app currently allows cleartext HTTP for development.

3) Build and run

- Launch on a device/emulator with Google Play services.
- Watch Logcat for the FCM registration token from `CustomFirebaseMessagingService`.

## Sending a Test Push

- From your backend (e.g., NestJS with `nestjs-firebase-admin`), send a message to the device token printed by the app.
- The app displays a system notification and opens a lightweight dialog activity for visibility.

## Notes and Tips

- Permissions: `POST_NOTIFICATIONS` is requested on Android 13+.
- Cleartext: `android:usesCleartextTraffic="true"` is enabled for dev; prefer HTTPS in production.
- Emulator: Use a Google APIs image; FCM may not work on AOSP-only images.
- API contract: Keep endpoints in sync with `ApiService`.

## Related

- Android app (this repo): https://github.com/hebertcisco/android-techniques-firebase-admin
- NestJS Firebase Admin library: https://github.com/hebertcisco/nestjs-firebase-admin
- Firebase Admin SDK docs: https://firebase.google.com/docs/admin/setup
- Firebase Cloud Messaging: https://firebase.google.com/docs/cloud-messaging
