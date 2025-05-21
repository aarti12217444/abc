# MindCheck – Self-Identifying Mental Health & Support System

MindCheck is an Android application designed to help users assess and track their mental well-being. It features mood tracking, mental health quizzes, fitness and hobby recommendations, and daily motivational support. The app also supports multilingual features and uses Firebase for secure authentication and data storage.

---

## 📌 Objective

The purpose of this app is to provide users with a private, simple, and supportive platform to understand and improve their mental health. MindCheck empowers users to self-assess, reflect, and take small steps toward emotional wellness.

---

## 🔧 Tech Stack

- **Language**: Kotlin  
- **UI Design**: XML  
- **Database**: Firebase Firestore  
- **Authentication**: Firebase Auth  
- **Notifications**: Android NotificationManager  
- **Other Tools**: SharedPreferences, Intent System  
- **IDE**: Android Studio  

---

## 🎯 Core Features

- **User Authentication**: Login/Sign-up using Firebase Authentication
- **Mood Tracker**: Daily mood input with emojis/sentiments and history tracking
- **Self-Assessment Quiz**: Multiple-choice questions to evaluate mental well-being
- **Fitness & Hobby Recommendations**: Activity suggestions like music, painting, dancing, meditation, family time, and spiritual routines
- **Multilingual Support**: English and Hindi language toggle
- **Progress Tracker**: Visual charts showing user mood patterns over time
- **Notifications & Alerts**: Daily reminders and custom alerts
- **Safe & Secure**: Firebase-backed user data handling

---

## 📲 How It Works

1. **Splash Screen**: Logo display with animation
2. **Authentication**: Users register or log in with email & password
3. **Homepage**: Access key features like:
   - Mood tracking
   - Fitness activities
   - Self-assessment quiz
   - Resources (music, bhajans, motivational videos)
4. **Language Toggle**: Seamlessly switch between English and Hindi
5. **Quiz**: Answer short multiple-choice questions, and get feedback
6. **Progress Monitoring**: Users can view previous moods and quiz scores
7. **Reminders**: Timed alerts for wellness check-ins or activities

---

## 🗂 Folder Structure

abc/
├── app/
│ └── src/
│ └── main/
│ ├── java/com/yourpackage/mindcheck/
│ │ ├── activities/
│ │ ├── firebase/
│ │ ├── models/
│ │ ├── adapters/
│ │ └── utils/
│ ├── res/
│ │ ├── layout/
│ │ ├── values/
│ │ ├── drawable/
│ ├── AndroidManifest.xml
├── build.gradle
└── README.md

yaml
Copy
Edit
