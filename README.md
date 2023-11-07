# Habit Tracker Application

The Habit Tracker Application is a versatile tool designed to help you establish and maintain habits, offering timed reminders and habit completion tracking. With this application, you can effectively manage multiple habits, create custom timers, and see your progress over time.



## Features
- **Create Habits:** Easily create habits.
- **Create Timers:** Easily create timers with customizable labels and time intervals.
- **Manage Multiple Timers:** Add and organize multiple timers to keep track of various habits and tasks.
- **Set Custom Intervals:** Tailor timers with specific time intervals that suit your requirements.
- **Reminders:** Get recurring notifications based on the selected time interval for each timer.
- **Habit Completion:** Mark your habits as done to track your progress over time.


## Endpoints (API)

### Create Habit
- **Endpoint:** `/habit/create`
- **Method:** POST
- **Description:** Create a new habit .


### List Habit
- **Endpoint:** `/habit/view`
- **Method:** GET
- **Description:** Retrieve a list of all habits.

### Get Habit by ID
- **Endpoint:** `/habit/view/{habitId}`
- **Method:** GET
- **Description:** Retrieve a specific habit's details by its ID.


### Update Habit
- **Endpoint:** `/habit/update/{habitId}`
- **Method:** PATCH
- **Description:** Update an existing habit's details.


### Delete Habit
- **Endpoint:** `/habit/delete/{habitId}`
- **Method:** DELETE
- **Description:** Delete a specific habit by its ID.

### Mark Habit as Done
- **Endpoint:** `/habit/{habitId}/done`
- **Method:** POST
- **Description:** Mark a habit as done, tracking its completion.

### Create Timer
- **Endpoint:** `/timer/{habitId}/timers`
- **Method:** POST
- **Description:** Create a new timer associated with a specific habit.


### List Timers
- **Endpoint:** `/timer/{habitId}/viewAllTimers`
- **Method:** GET
- **Description:** Retrieve a list of all timers.

### Get Timer by ID
- **Endpoint:** `/timer/{habitId}/viewTimer/{timerId}`
- **Method:** GET
- **Description:** Retrieve a specific timer's details by its ID.


### Update Timer
- **Endpoint:** `/timer/{habitId}/updateTimer/{timerId}`
- **Method:** PATCH
- **Description:** Update an existing timer's details.


### Delete Timer
- **Endpoint:** `/timer/{habitId}/deleteTimer/{timerId}`
- **Method:** DELETE
- **Description:** Delete a specific timer by its ID.



## Installation

1. Clone the repository to your local machine:https://github.com/Jiyjo-jose/habit-tracker.
2. Open the project.
3. Ensure you have Java and Maven installed.
