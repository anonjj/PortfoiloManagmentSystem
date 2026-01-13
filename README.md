# Portfolio Management System

A comprehensive Java Swing application for managing student portfolios, including exam preparation, learning tracking, and course discovery.

## 1. Problem Statement

In the modern digital learning landscape, students require a centralized system to manage their personal growth, track skills, prepare for competitive exams, and browse useful courses. The absence of such a platform often leads to scattered efforts and inefficiency. This project aims to solve this by building a Java-based Portfolio Management System.

## 2. Project Description

The Portfolio Management System is a Java Swing-based desktop application that enables users to sign up, log in, edit profiles, take mock exams, track their learning progress, and explore categorized online courses. It features a clean GUI, database integration with MySQL, and modular DAO classes for separation of logic.

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

## 3. Details of Development

### i. Methods/Techniques/Algorithm Used

The system uses the MVC design pattern with DAOs for data access and Swing for GUI. Event-driven programming is used for user interaction. SQL queries are used to perform CRUD operations. Leaderboard sorting and profile update mechanisms are also implemented.

### ii. Tools/Software/Libraries Used

- Java (Swing)
- MySQL
- JDBC
- IntelliJ IDEA or Eclipse
- MySQL Workbench
- GitHub (for version control)

### iii. Data/Charts/Datasets Used

- Udemy course dataset
- Mock question bank for various exams (stored in MySQL)
- Exam date table (application/exam/result dates)

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

## 4. Application Advantages and Limitations

**Advantages:**
- Centralized student portfolio tracking
- Practice MCQs with leaderboard
- Searchable and filterable course browsing
- User-friendly GUI and modular code

**Limitations:**
- Requires local MySQL setup
- No cloud sync or mobile version
- Limited to structured exam categories and skill inputs

## 5. Future Scope

- Deploy as a web app with Spring Boot
- Add resume builder and job board
- Integrate APIs like LinkedIn Learning, Coursera
- Gamify learning tracker
- Support multi-language interface

## 6. References

- Java Oracle Documentation
- MySQL JDBC API
- Udemy Dataset (Kaggle)
- GeeksforGeeks and Stack Overflow

## License

This project is for educational purposes.
