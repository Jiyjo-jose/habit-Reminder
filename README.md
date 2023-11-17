# Habit Tracker Application

The Habit Tracker Application is a versatile tool designed to help you establish and maintain habits, offering timed reminders and habit completion tracking. With this application, you can effectively manage multiple habits.
## Overview
This repository contains the source code for a Habit Tracker application. The application is designed to help users track and build positive habits through reminders.

## Features
- **Habit Management**: Create, update, and delete habits.
- **Reminder Days**: Specify which days reminders should be sent for each habit.
- **Reminder Time**: Set a specific time for daily reminders.
- **Interval**: Define the frequency of reminders.
- **End Date**: Choose an end date for a habit.

## Endpoints (API)

### Habit

#### Create Habit

- **Endpoint:** `/habit/createHabit`
- **Method:** POST
- **Description:** Create a new habit.

#### Get All Habits

- **Endpoint:** `/habit/viewHabit`
- **Method:** GET
- **Description:** Retrieve a list of all habits.

#### Update Habit

- **Endpoint:** `/habit/{habitId}/editHabit`
- **Method:** PUT
- **Description:** Update an existing habit's details.

#### Delete Habit

- **Endpoint:** `/habit/delete/{habitId}`
- **Method:** DELETE
- **Description:** Delete a specific habit by its ID.


### Reminder Days

#### Add Reminder Days to Habit

- **Endpoint:** `/habit/{habitId}/createReminderDays`
- **Method:** POST
- **Description:** Add reminder days to a habit.

#### View Reminder Days of a Habit

- **Endpoint:** `/habit/{habitId}/allReminderDays`
- **Method:** GET
- **Description:** View reminder days of a habit.

#### Complete Reminder Day

- **Endpoint:** `/habit/{habitId}/reminderDays/{reminderDayId}/complete`
- **Method:** PUT
- **Description:** Mark a reminder day as completed.

#### Get Completed Reminder Days

- **Endpoint:** `/habit/{habitId}/completedReminderDays`
- **Method:** GET
- **Description:** Retrieve completed reminder days for a habit.

#### Get Incomplete Reminder Days

- **Endpoint:** `/habit/{habitId}/incompleteReminderDays`
- **Method:** GET
- **Description:** Retrieve incomplete reminder days for a habit.

#### Delete Reminder Day

- **Endpoint:** `/habit/{habitId}/reminderDays/{reminderDayId}`
- **Method:** DELETE
- **Description:** Delete a specific reminder day.


## Installation


1. Clone the repository to your local machine:https://github.com/Jiyjo-jose/habit-tracker.

2. Open the project.

3. Ensure you have Java and Maven installed.
