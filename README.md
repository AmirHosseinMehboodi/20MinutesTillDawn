# 🌘 20 Minutes Till Dawn – Java LibGDX Game Project

## 📘 Project Overview

A complete, custom-built 2D game developed in **Java** using the **LibGDX** framework, inspired by *20 Minutes Till Dawn*. This was created as the graphics-focused final project for the **Advanced Programming** course in the **Computer Engineering Department at Sharif University of Technology** (Spring 2025, Term 2).

The project features a polished user interface, dynamic combat system, multiple enemies with AI behavior, upgradeable abilities, persistent save system, multilingual support, and animated visual effects — all designed and implemented following the **Model-View-Controller (MVC)** architecture.

---

## 👤 Author
**Name**: Amirhossein Mehboodi
**Course**: Advanced Programming (Graphics Assignment)  
**Instructor**: Dr. Mohammad Amin Fazli  
**University**: Sharif University of Technology

---

## 🎮 Key Features

### 🧩 Game Systems
- **Character Selection**: 12 playable heroes with distinct health/speed stats
- **Weapon Mechanics**: 3 weapons with different fire rates, reload mechanics, and projectile behaviors
- **Ability System**: 5 upgradeable abilities like damage boost, health increase, speed buff, etc.
- **Enemy AI**: Includes basic enemies and a boss fight, each with unique behaviors and attack patterns
- **XP & Leveling**: Dynamic XP system with random or selectable upgrades at each level

### 📋 UI & Menus
- **Full Menu Suite**: Login/Register, Main, Settings, Profile, Pre-Game, Pause, Scoreboard
- **Interactive Scoreboard**: Sortable by multiple criteria, with visual markers for top players and current user
- **Settings**: Key remapping, music/SFX control, auto-reload toggle, black & white mode, language selection
- **Multilingual Support**: English and Japanese, implemented using enums for scalable localization

### 💾 Save & Persistence
- **SQLite**: Stores user profiles, settings, scores
- **JSON**: Saves game state, including player progress, current run, etc.
- **Resume System**: Automatically restores progress from last save on game start

### 🎨 Visual & UX Enhancements
- Custom **bitmap fonts** and **mouse cursor**
- Smooth **animation** transitions for shooting, reloading, death, damage, and XP gain
- Dynamic **lighting** effect around player to simulate limited vision
- Responsive **Scene2D UI** with clean layout and color feedback

### 🧪 Cheat Tools (Dev-Only)
- Add XP
- Skip to boss fight
- Refill HP (if not full)
- Reduce timer

---

## 🛠 Technologies Used

- **Java 24**
- **LibGDX (Game Engine)**
- **Scene2D UI**
- **SQLite (via JDBC)**
- **Gradle Build Tool**

---

## 🚀 How to Run

Ensure **JDK 17+** is installed. Then clone the project and run:

```bash
./gradlew desktop:run
