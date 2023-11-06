# Proposal

> 2023.11.06. Mon  
> Presentor: Hyunseo Park

- [Proposal](#proposal)
  - [Project Description](#project-description)
    - [1. Application Name](#1-application-name)
      - [Aging Population: Entering the Era of Aging Society](#aging-population--entering-the-era-of-aging-society)
      - [Memoriary](#memoriary)
    - [2. Main Application Concept](#2-main-application-concept)
      - [Diary](#diary)
      - [Exercise](#exercise)
    - [3. Basic UI/UX](#3-basic-ui-ux)
      - [a. Start Page](#a-start-page)
      - [b. Membership](#b-membership)
      - [c. MyPage](#c-mypage)
      - [d. Diary](#d-diary)
      - [e. To-Do List](#e-to-do-list)
      - [f. Quiz](#f-quiz)
      - [g. Exercise](#g-exercise)
    - [4. Expected Components](#4-expected-components)
    - [5. Expected Time Frame](#5-expected-time-frame)
    - [6. Role & Responsibilities](#6-role---responsibilities)
  - [Feedback](#feedback)

## Project Description

### 1. Application Name

#### Aging Population: Entering the Era of Aging Society

Korea is currently in the preliminary stages of transitioning into a super-aging society. This transition signifies a growing proportion of individuals aged 65 and above, with expectations of an even more rapid increase in the future. Among the elderly population, there is a noticeable increase in those experiencing difficulties in their daily lives due to dementia.

#### Memoriary

**Memoriary** is is a combination of the words "Memory" and "Diary." It was coined with the intention of preventing memory loss among our primary user group, the elderly, by using our app's core feature, the Diary.

### 2. Main Application Concept

#### Diary

- **Diary + To-Do List**: Memoriary combines a diary and a to-do list to help users organize their tasks and record their thoughts.
- **Memory Retention Quizzes**: Engage in memory retention quizzes designed to exercise the mind and improve cognitive health.

#### Exercise

- **Promotion of Physical and Cognitive Health**: The app encourages users to stay physically and mentally active through various exercises.
- **Exercise Options**: Users can engage in exercises such as "Shake It Up" and "Clapping" to prevent dementia.

### 3. Basic UI/UX

#### a. Start Page

- **Sign-In Page**: Existing users can log in to their accounts.
- **Sign-Up Page**: New users can create their accounts.

#### b. Membership

- **Sign-in with ID & Password**: Enter your user ID and password to log in.
- **Sign Up Page**: Provide your name, birth date, user ID, password, and agree to the terms and conditions during sign-up.
- **Completed Sign Up**: Confirmation message after successful sign-up.

#### c. MyPage

- **Check User's Information**: View user details, including name, age, and profile image.
- **Change My Profile**: Modify user information as needed.
- **View Application Announcements**: Access and read announcements related to the application.
- **1:1 Question**: Utilize the 1:1 Question and Answer feature for personalized assistance and support.
- **Log Out**: Log out from the application.

#### d. Diary

- **Typing for Diary Entries**: Traditional typing for journal entries.
- **Voice Recognition for Diary Entries**: Users can speak their diary entries, transcribed using Speech-to-Text (STT) technology.
- **Visual Enhancement with Karlo API**: Automatically generate captivating thumbnails for each diary entry using the Karlo API.

#### e. To-Do List

- **Input To-Do Tasks via Typing**: Enter to-do tasks by typing them.
- **Input To-Do Tasks via Voice Recognition**: Speak your to-do tasks, and our system will transcribe them.
- **Mark as Completed**: Check off or mark tasks as completed.

#### f. Quiz

- **Multiple-Choice Quiz**: Test your knowledge with multiple-choice questions.
- **True/False Quiz**: Engage in true or false questions.
- **Open-Ended Quiz**: Participate in open-ended questions that require written responses.
- **Popup Answer Confirmation**: Verify your answers with a pop-up confirmation.

#### g. Exercise

- **Shake It Up**: Shake your phone to engage in exercises.
- **Clapping**: Encourage clapping exercises for dementia prevention.

### 4. Expected Components

- Karlo API
- Speech-to-Text (STT) / Text-to-Speech (TTS)
- MySQL
- GPT API
- Android Sensor

### 5. Expected Time Frame

- Join: W10
- Login/Logout: W10
- My Page: W11
- Diary and To-Do: W12
- Quiz: W13
- Exercise: W14
- Architecture Design: W10~14
- Testing, Defect Testing: W14

### 6. Role & Responsibilities

- Siheon: App development - Exercise, My Page, Nav Bar
- Hyunseo: App development - Diary, Quiz
- Jeonghyeon: App development - To-Do, Sign In

## Feedback

- Is Karlo supported in English?
- What is MySQL going to be used for, and are you planning to set up a server?
- How does the app recognize clapping?
- How will you handle open-ended questions for subjective answers?
