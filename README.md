# Kreeda-Prerana Scout 🏃‍♂️📱

> MindMatrix VTU Internship Program - Project 88
> Android App Development using GenAI

## 🎯 Problem Statement
Talented young athletes in rural schools often go unnoticed because there is no structured way to record their physical milestones like sprint times and jump heights. Coaches and scouts cannot identify "Diamonds in the Rough" without a historical data trail of their performance.

## 💡 Our Solution - The Vision
**Kreeda-Prerana Scout** is a grassroots sports talent tracker. It acts as a **"Digital Scout"** for physical education teachers. 

The app allows teachers to create profiles for students and log their performance in standard athletic tests. Over time, it creates a **"Talent Curve"**, making it easy to spot students who have the potential to compete at state or national levels.

## ✨ Key Features
- **Athlete Profile**: Create profiles with Name, Age, and primary sport (Kabaddi, Athletics, etc.)
- **Trial Logger**: High-precision chronometer and distance logger for sprints, long jumps, etc.
- **Milestone Badges**: Automatically awards "District Level Ready" badges based on preset benchmarks
- **Leaderboard**: Internal school ranking to boost healthy competition
- **Talent Curve**: Visual graph showing athlete's performance growth over time
- **Batch Entry**: Add performance data for entire class of 30 students at once

## 🤖 GenAI Integration - Google Gemini API
This project leverages **Google Gemini Pro** to act as an "AI Sports Coach" for rural students.

**How GenAI is used:**
1. **Performance Analysis**: After a teacher logs trial data, Gemini analyzes the "Talent Curve" and generates a personalized feedback report
2. **Strength & Weakness Detection**: AI identifies if an athlete is improving in sprints but weak in endurance
3. **Training Recommendations**: Gemini suggests specific drills. Example: "For improving 100m sprint time, practice 30m block starts 3 times a week"
4. **Motivational Reports**: Generates easy-to-understand reports for students/parents in simple language

**Sample Gemini Prompt Used:**
**Sample AI Output:**


## 🛠️ Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose, Material Design 3
- **Database**: Room DB for storing athlete performance history offline
- **Timer**: High-precision Chronometer API for timing races to 2 decimal places
- **Analytics**: Custom sorting algorithms for leaderboard
- **AI Integration**: Google Gemini API `com.google.ai.client.generativeai:0.9.0`
- **IDE**: Android Studio

## 📱 App Usage & User Flow
1. **Teacher Login**: PE teacher creates account
2. **Add Athlete**: Create profile for each student
3. **Log Trial**: Use stopwatch to record sprint time or enter jump distance
4. **View Talent Curve**: Check student's progress graph
5. **Generate AI Report**: Tap button to get Gemini-powered coaching tips
6. **Check Leaderboard**: See school-level rankings

## 🎯 Impact Goals
- **Khelo India Support**: Identifying rural talent early to feed into national academies
- **Physical Literacy**: Encouraging a culture of fitness and record-keeping in schools
- **Equal Opportunity**: Ensuring a child in a remote village has a digital record of their talent

## ⚙️ Setup & Installation
1. Clone the repo: `git clone https://github.com/Rajsingh2801/Kreeda-Prerana-Scout.git`
2. Open in Android Studio
3. Add your Gemini API Key in `local.properties`:
4. Sync Gradle and run the app

## ✅ Success Criteria Met
- Timer accurate to two decimal places ✅
- Batch Entry for entire class of 30 students ✅
- Clear and easy to interpret "Talent Curve" graph ✅

## 👨‍💻 Developed By
**Raj Singh** 
MindMatrix VTU Internship Program 2026   

- Added 100m Sprint Timer
