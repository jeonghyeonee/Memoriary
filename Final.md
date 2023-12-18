# Final

> 2023.12.18. Mon  
> Presentor: Hyunseo Park

## Project Description

### 1. Application Name

#### Aging Population: Entering the Era of Aging Society

Korea is currently in the preliminary stages of transitioning into a super-aging society. This transition signifies a growing proportion of individuals aged 65 and above, with expectations of an even more rapid increase in the future. Among the elderly population, there is a noticeable increase in those experiencing difficulties in their daily lives due to dementia.

#### Memoriary

**Memoriary** is is a combination of the words "Memory" and "Diary." It was coined with the intention of preventing memory loss among our primary user group, the elderly, by using our app's core feature, the Diary.

### 2. Role & Responsibilities

- [Siheon](https://github.com/siheon0411): App development - Diary, Nav Bar
- [Hyunseo](https://github.com/hyunwestpark): App development - Exercise, Quiz, My Page
- [Jeonghyeon](https://github.com/jeonghyeonee): App development - To-Do, Sign In, Nav Bar

### 3. Application Features

#### Diary

- **Diary + To-Do List**: Memoriary combines a diary and a to-do list to help users organize their tasks and record their thoughts.
- **Memory Retention Quizzes**: Engage in memory retention quizzes designed to exercise the mind and improve cognitive health.

#### Exercise

- **Promotion of Physical and Cognitive Health**: The app encourages users to stay physically and mentally active through various exercises.
- **Exercise Options**: Users can engage in exercises such as "Shake It Up" and "Clapping" to prevent dementia.

#### Main Components

- ~~Karlo API~~ -> DALLE
- Speech-to-Text (STT) / Text-to-Speech (TTS)
- ~~MySQL~~ -> RoomDB
- GPT API
- Android Sensor

#### a. Membership

- **Sign-in with ID & Password**: Enter your user ID and password to log in.
- **Sign Up Page**: Provide your name, birth date, user ID, password, and agree to the terms and conditions during sign-up.
- **Completed Sign Up**: Confirmation message after successful sign-up.
- **FingerPrint Sign In**:
  Auto Sign in using user's FingerPrint

#### c. MyPage

- **User information**:
  - User's name is appeared
  - User's profile picture is appeared
- **Changed user's profile picture**: User can change the profile picture
- **Sign out**
  : User can sign out the application

#### d. Diary

- **Typing for Diary Entries**: Traditional typing for journal entries.
- **Voice Recognition for Diary Entries**: Voice InputRecognizing that some seniors may face typing challenges, our app allows users to speak their diary entries, transcribed using Speech-to-Text (STT) technology.
- **Visual Enhancement with OpenAI DALL-E2 API**: Diary Thumbnails to make diary entries more visually engaging and memorable, our app incorporates the DaLL-E2 API, automatically generating captivating thumbnails for each diary entry.

#### e. To-Do List

- **Input To-Do Tasks via Typing**: Enter to-do tasks by typing them.
- **Input To-Do Tasks**: Once you've aompleted a task, you can check it off or mark it as done.
- **Show the Calendar View**: See the Todo list through the Calendar
- **Show the Done List**:
  Show the checked list in DoneList

#### f. Quiz

- **Open AI API used for Quiz Generation**:
  - Based on the contents of today's diary, three questions are generated.
  - The correct answer is also set with respect to each question.
- **Generated Questions**:
  - Q1. Select one correct answer among five choices.
  - Q2. Select one incorrect answer among five choices.
  - Q3. Given expression is true of false?
- **Grading the Answer**: User's answer would be graded through the correct answer

#### g. Exercise

- **Phone shaking exercise**:
  - The service gives an instruction to the user regarding shaking phone exercise
  - User has to follow the instruction to be successful on the exercise
  - When the user successfully completes the exercise, the user will be notified by the dialog popup it was successful

## Feedback
