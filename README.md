# Portfolio Management System

A comprehensive Java Swing application for managing student portfolios, including exam preparation, learning tracking, and course discovery.

## Features

- **User Authentication**: Secure login and signup system
- **Profile Management**: View and edit user profiles
- **Exam Preparation**: Mock questions for JEE, CAT, NEET, GMAT, LSAT, GATE, and UPSC with leaderboards
- **Learning Tracker**: Track skills and proficiency levels
- **Course Discovery**: Browse and filter courses by category, level, and price

## Project Structure

```
src/
└── com/portfolio/
    ├── PortfolioApp.java          # Main entry point
    ├── model/                      # Data models
    │   ├── UserAccount.java
    │   └── MockQuestion.java
    ├── dao/                        # Database access objects
    │   ├── UserDAO.java
    │   ├── CourseDAO.java
    │   ├── ExamDAO.java
    │   └── LearningTrackerDAO.java
    └── ui/                         # User interface
        └── PortfolioManagementUI.java
```

## Prerequisites

- Java 11 or higher
- MySQL database
- JDBC MySQL Connector

## Database Setup

1. Create a MySQL database named `portfolio_db`
2. Create the following tables:
   - `users` - User accounts
   - `exam_dates` - Exam schedule information
   - `mock_questions` - Exam practice questions
   - `exam_scores` - User exam scores
   - `learning_tracker` - User skills tracking
   - `udemy_courses` - Course catalog

## How to Compile

```bash
javac -d bin -sourcepath src src/com/portfolio/PortfolioApp.java
```

## How to Run

```bash
java -cp bin com.portfolio.PortfolioApp
```

## Technologies Used

- **Java Swing** - GUI framework
- **JDBC** - Database connectivity
- **MySQL** - Database

## License

This project is for educational purposes.
