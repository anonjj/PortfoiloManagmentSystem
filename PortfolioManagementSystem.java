package jdbc;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URI; //used to use data
import java.util.Date;
import java.util.Enumeration; //exam panel
import java.util.HashMap; // used in courses panel
import java.util.List;
import java.util.Map; //courses
import java.util.ArrayList;

public class PortfolioManagementSystem {
    // Main components of the application
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String userName = "";
    private String userEmail = "";
    private UserAccount currentUser;

 
    public PortfolioManagementSystem() {
        prepareGUI();
    }

    // Sets up the main application GUI
    private void prepareGUI() {
        // Initialize the main frame
        mainFrame = new JFrame("Portfolio Management System");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create card layout and panel to switch between screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Set up the welcome, login, signup, and home panels
        setupWelcomePanel();
        setupLoginPanel();
        setupSignupPanel();
        setupHomePage();
        
        // Add card panel to the main frame and display
        mainFrame.add(cardPanel);
        mainFrame.setVisible(true);
    }

    // Creates the welcome screen with options to login or signup
    private void setupWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(new Color(162, 205, 90));  // Light green background
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create welcome screen components
        JLabel titleLabel = new JLabel("P's in a Pod", SwingConstants.CENTER); // used in the names in login page
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        
        JLabel subtitleLabel = new JLabel("Portfolio Management System", SwingConstants.CENTER); // used in the names in login page
        subtitleLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(150, 40));
        
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        signupButton.setPreferredSize(new Dimension(150, 40));
        
        // Add components to panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        welcomePanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        welcomePanel.add(subtitleLabel, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(30, 10, 10, 10);
        welcomePanel.add(loginButton, gbc);
        
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        welcomePanel.add(signupButton, gbc);
        
        // Add action listeners
        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        signupButton.addActionListener(e -> cardLayout.show(cardPanel, "Signup"));
        
        // Add the welcome panel to the card layout
        cardPanel.add(welcomePanel, "Welcome");
    }
    
    // Creates the login screen
    private void setupLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(162, 205, 90));  // Light green background
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create components for login screen
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        // Add components to panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        loginPanel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(162, 205, 90));
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);
        
        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
        
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Please enter both email and password",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            UserAccount account = UserDAO.authenticate(email, password);
            if (account != null) {
            	currentUser = account;
            	userName = account.getName();
            	userEmail = account.getEmail();

                int userId = account.getId(); // Your unique user ID!
        
                JOptionPane.showMessageDialog(mainFrame,
                    "Login successful! Welcome back, " + userName + " (ID: " + userId + ")",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        
                cardLayout.show(cardPanel, "Home");
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                    "Invalid email or password",
                    "Authentication Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        // Add action listener to back button
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));
        
        // Add the login panel to the card layout
        cardPanel.add(loginPanel, "Login");
    }
    
    // Creates the signup screen
    private void setupSignupPanel() {
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBackground(new Color(162, 205, 90));  // Light green background
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create components for signup screen
        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField(20);
        
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");
        
        // Add components to panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        signupPanel.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        signupPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        signupPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        signupPanel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        signupPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        signupPanel.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        signupPanel.add(confirmPasswordLabel, gbc);
        
        gbc.gridx = 1;
        signupPanel.add(confirmPasswordField, gbc);
        
        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(162, 205, 90));
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        signupPanel.add(buttonPanel, gbc);
        
        // Add action listener to signup button
        signupButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
        
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Please fill in all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (UserDAO.userExists(email)) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Email already registered. Please login or use a different email.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            boolean success = UserDAO.createUser(name, email, password);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame,
                    "Account created successfully! Please login now.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                cardLayout.show(cardPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                    "Signup failed. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add action listener to back button
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Welcome"));
        
        // Add the signup panel to the card layout
        cardPanel.add(signupPanel, "Signup");
    }

    static class UserAccount {
    private int id;
    private String name;
    private String email;
    private String password;

    public UserAccount(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserAccount(String name, String email, String password) {
        this(-1, name, email, password);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

    
    // Creates the main home page with tabs
    private void setupHomePage() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(162, 205, 90));

        // Create header panel with title and logout button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(162, 205, 90));
        
        JLabel titleLabel = new JLabel("P's in a Pod", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            // Reset user info and return to welcome screen
            userName = "";
            userEmail = "";
            cardLayout.show(cardPanel, "Welcome");
        });
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(logoutButton, BorderLayout.EAST);

        // Create tabbed pane for different sections
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add tabs with different panels
        tabbedPane.addTab("Profile", createProfileDashboard());
        tabbedPane.addTab("Exams", createExamsPanel());
        tabbedPane.addTab("Learning Tracker", createLearningTrackerPanel());
        tabbedPane.addTab("Courses", createCoursesPanel());
        //tabbedPane.addTab("Resume/CV", createResumePanel());
        //tabbedPane.addTab("Job Market", createJobMarketPanel());

        // Add components to the home panel
        homePanel.add(headerPanel, BorderLayout.NORTH);
        homePanel.add(tabbedPane, BorderLayout.CENTER);

        // Add the home panel to the card layout
        cardPanel.add(homePanel, "Home");
    }

    // Creates the profile dashboard panel
    private JPanel createProfileDashboard() {
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(new Color(162, 205, 90));
        
        // Profile information panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Profile Information"));
        
        // Add user information labels
        infoPanel.add(new JLabel("Name:"));
        JLabel nameValueLabel = new JLabel();
        infoPanel.add(nameValueLabel);
        
        infoPanel.add(new JLabel("Email:"));
        JLabel emailValueLabel = new JLabel();
        infoPanel.add(emailValueLabel);
        
        infoPanel.add(new JLabel("Member Since:"));
        infoPanel.add(new JLabel(new Date().toString()));
        
        // Update labels with current user info
        // This needs to be done before displaying the panel
        profilePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                nameValueLabel.setText(userName);
                emailValueLabel.setText(userEmail);
            }
        });    
        // Button to edit profile
        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(e -> {
            cardPanel.add(createEditProfilePanel(), "EditProfile");
            cardLayout.show(cardPanel, "EditProfile");
        });
        
        // Add components to the profile panel
        profilePanel.add(infoPanel, BorderLayout.NORTH);
        profilePanel.add(editProfileButton, BorderLayout.SOUTH);
        
        return profilePanel;
    }

//Panel to Edit User Profile Info
    private JPanel createEditProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(162, 205, 90));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(currentUser.getName(), 20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(currentUser.getEmail(), 20);

        JLabel passwordLabel = new JLabel("New Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        JPasswordField confirmField = new JPasswordField(20);

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(confirmLabel, gbc);
        gbc.gridx = 1;
        panel.add(confirmField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(backButton, gbc);
        gbc.gridx = 1;
        panel.add(saveButton, gbc);

        saveButton.addActionListener(e -> {
            String newName = nameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            if (newName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(panel, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean updated = CourseDAO.ExamDAO.updateUser(currentUser.getId(), newName, newEmail, confirmPassword);
            if (updated) {
                JOptionPane.showMessageDialog(panel, "Profile updated successfully!");
                currentUser = new UserAccount(currentUser.getId(), newName, newEmail, newPassword);
                userName = newName;
                userEmail = newEmail;
                cardLayout.show(cardPanel, "Home");
            } else {
                JOptionPane.showMessageDialog(panel, "Update failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        return panel;
    }



	// Creates the exams panel
 // Enhanced Exams Panel with Important Dates + Mock Questions
    private JPanel createExamsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(162, 205, 90));

        // TOP: Dropdown and Load button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(162, 205, 90));

        JComboBox<String> examSelector = new JComboBox<>(new String[]{"JEE", "CAT", "NEET", "GMAT", "LSAT", "GATE", "UPSC"});
        JButton loadButton = new JButton("Load Mock Questions");

        topPanel.add(new JLabel("Select Exam:"));
        topPanel.add(examSelector);
        topPanel.add(loadButton);

        // CENTER LEFT: Date area + questions
        JPanel centerPanel = new JPanel(new BorderLayout());
        JTextArea dateArea = new JTextArea(4, 40);
        dateArea.setEditable(false);
        dateArea.setLineWrap(true);
        centerPanel.add(dateArea, BorderLayout.NORTH);

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        JScrollPane questionScroll = new JScrollPane(questionsPanel);
        centerPanel.add(questionScroll, BorderLayout.CENTER);

        // CENTER RIGHT: Leaderboard
        JTextArea leaderboardArea = new JTextArea(8, 20);
        leaderboardArea.setEditable(false);
        JScrollPane leaderboardScroll = new JScrollPane(leaderboardArea);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.add(centerPanel, BorderLayout.CENTER);
        centerWrapper.add(leaderboardScroll, BorderLayout.EAST);

        // BOTTOM: Submit button
        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        bottomPanel.add(submitButton);

        // Add everything to main panel
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerWrapper, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // BUTTON LOGIC
        loadButton.addActionListener(e -> {
            String selectedExam = (String) examSelector.getSelectedItem();
            dateArea.setText(jdbc.PortfolioManagementSystem.CourseDAO.ExamDAO.getExamDates(selectedExam));
            questionsPanel.removeAll();

            List<MockQuestion> questions = jdbc.PortfolioManagementSystem.CourseDAO.ExamDAO.getMockQuestions(selectedExam);
            for (MockQuestion mq : questions) {
                JPanel qPanel = new JPanel();
                qPanel.setLayout(new BoxLayout(qPanel, BoxLayout.Y_AXIS));

                JLabel questionLabel = new JLabel("<html><b>Q:</b> " + mq.question + "</html>");
                questionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

                questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                ButtonGroup group = new ButtonGroup();
                JRadioButton optA = new JRadioButton("A. " + mq.optionA);
                JRadioButton optB = new JRadioButton("B. " + mq.optionB);
                JRadioButton optC = new JRadioButton("C. " + mq.optionC);
                JRadioButton optD = new JRadioButton("D. " + mq.optionD);

                group.add(optA); group.add(optB); group.add(optC); group.add(optD);

                qPanel.add(questionLabel);
                qPanel.add(optA);
                qPanel.add(optB);
                qPanel.add(optC);
                qPanel.add(optD);

                // Visual spacing (optional)
                qPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                qPanel.putClientProperty("group", group);
                qPanel.putClientProperty("correct", mq.correctOption);

                questionsPanel.add(qPanel);
            }

            List<String[]> leaderboard = jdbc.PortfolioManagementSystem.CourseDAO.ExamDAO.getLeaderboard(selectedExam);
            StringBuilder lb = new StringBuilder("\uD83C\uDFC6 Leaderboard:\n");
            int rank = 1;
            for (String[] entry : leaderboard) {
                lb.append(rank++).append(". ").append(entry[0]).append(" - ").append(entry[1]).append("/5\n");
            }
            leaderboardArea.setText(lb.toString());

            questionsPanel.revalidate();
            questionsPanel.repaint();
        });

        submitButton.addActionListener(e -> {
            int correct = 0; // the correction count initilized
            for (Component comp : questionsPanel.getComponents()) {
                if (comp instanceof JPanel qPanel) {
                    ButtonGroup group = (ButtonGroup) qPanel.getClientProperty("group");
                    char correctOption = (char) qPanel.getClientProperty("correct");

                    Enumeration<AbstractButton> buttons = group.getElements();//
                    int i = 0;
                    while (buttons.hasMoreElements()) {
                        AbstractButton button = buttons.nextElement();
                        if (button.isSelected() && correctOption == ('A' + i)) {
                            correct++;
                            button.setForeground(Color.GREEN);
                        } else if (button.isSelected()) {
                            button.setForeground(Color.RED);
                        }
                        i++;
                    }
                }
            }

            String examName = (String) examSelector.getSelectedItem();
            if (currentUser != null) {
                jdbc.PortfolioManagementSystem.CourseDAO.ExamDAO.saveScore(examName, currentUser.getEmail(), correct);
            }
            JOptionPane.showMessageDialog(panel, "You got " + correct + " out of 5 correct.");
        });

        return panel;
    }

static class MockQuestion {
    String question, optionA, optionB, optionC, optionD;
    char correctOption;

    public MockQuestion(String question, String optionA, String optionB, String optionC, String optionD, char correctOption) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }
}
    
// Creates the learning tracker panel
    private JPanel createLearningTrackerPanel() {
        JPanel learningPanel = new JPanel(new BorderLayout());
        learningPanel.setBackground(new Color(162, 205, 90));

        String[] columnNames = {"Skill", "Proficiency", "Hours Studied", "Last Practiced"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable skillsTable = new JTable(model);
        skillsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(skillsTable);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Skill Name:"));
        JTextField skillNameField = new JTextField();
        inputPanel.add(skillNameField);

        inputPanel.add(new JLabel("Proficiency:"));
        String[] proficiencyLevels = {"Beginner", "Intermediate", "Advanced", "Expert"};
        JComboBox<String> proficiencyCombo = new JComboBox<>(proficiencyLevels);
        inputPanel.add(proficiencyCombo);

        JButton addButton = new JButton("Add Skill");
        JButton updateButton = new JButton("Update Selected");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        learningPanel.add(scrollPane, BorderLayout.CENTER);
        learningPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Load skills when the panel is shown
        learningPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                model.setRowCount(0); // clear
                UserAccount user = currentUser; // hacky reuse
                if (user != null) {
                    for (Object[] row : LearningTrackerDAO.getSkillsByUser(user.getId())) {
                        model.addRow(row);
                    }
                }
            }
        });

        addButton.addActionListener(e -> {
            String skill = skillNameField.getText().trim();
            String prof = (String) proficiencyCombo.getSelectedItem();

            if (!skill.isEmpty()) {
                // 🔐 Prevent duplicate skill entries
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).toString().equalsIgnoreCase(skill)) {
                        JOptionPane.showMessageDialog(mainFrame, "Skill already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                UserAccount user = currentUser;
                if (user != null && LearningTrackerDAO.addSkill(user.getId(), skill, prof)) {
                    model.addRow(new Object[]{skill, prof, 0, new Date()});
                    skillNameField.setText("");
                    proficiencyCombo.setSelectedIndex(0); // Reset dropdown
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to add skill.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(e -> {
            int row = skillsTable.getSelectedRow();
            if (row >= 0) {
                String skill = (String) model.getValueAt(row, 0);
                String prof = (String) model.getValueAt(row, 1);
                int hours = Integer.parseInt(model.getValueAt(row, 2).toString());
                
                UserAccount user = currentUser;
                if (user != null && LearningTrackerDAO.updateSkill(user.getId(), skill, prof, hours)) {
                    model.setValueAt(new Date(), row, 3);
                }
                else {
                    JOptionPane.showMessageDialog(mainFrame, "Failed to update skill.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return learningPanel;
    }
    

    // Creates the courses panel
    private JPanel createCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(162, 205, 90));

        String[] categories = {"Business Finance", "Graphic Design", "Musical Instrument", "Web Development"};
        String[] levels = {"All Levels", "Beginner Level", "Intermediate Level", "Expert Level"};

        JPanel categoryPanel = new JPanel(new FlowLayout());
        JComboBox<String> difficultyBox = new JComboBox<>(levels);
        JLabel categoryLabel = new JLabel("Subject: None");

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Course Title", "Price", "Duration", "Link"}, 0);
        JTable courseTable = new JTable(tableModel) {
            private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
                return (column == 3) ? String.class : super.getColumnClass(column);
            }
        };

        courseTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = courseTable.rowAtPoint(e.getPoint());
                int col = courseTable.columnAtPoint(e.getPoint());
                if (col == 3) {
                    String url = (String) courseTable.getValueAt(row, col);
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Couldn't open link 😞");
                    }
                }
            }
        });

        JScrollPane tableScroll = new JScrollPane(courseTable);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(240, 240, 240));

        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);
        JButton priceFilterButton = new JButton("Apply Price Filter");

        filterPanel.add(new JLabel("Min Price:"));
        filterPanel.add(minPriceField);
        filterPanel.add(new JLabel("Max Price:"));
        filterPanel.add(maxPriceField);
        filterPanel.add(priceFilterButton);

        final String[] selectedCategory = {null};
        final String[] selectedLevel = {"All Levels"};

        Map<String, String> categoryMapping = new HashMap<>();
        categoryMapping.put("Web Development", "web development");
        categoryMapping.put("Graphic Design", "graphic design");
        categoryMapping.put("Business Finance", "business finance");
        categoryMapping.put("Musical Instrument", "musical instruments");

        for (String category : categories) {
            JButton btn = new JButton(category);
            btn.addActionListener(e -> {
                String level = (String) difficultyBox.getSelectedItem();
                String dbCategory = categoryMapping.get(category);
                selectedCategory[0] = dbCategory;
                selectedLevel[0] = level;
                refreshCoursesTable(dbCategory, level, tableModel);
                categoryLabel.setText("Subject: " + dbCategory);
            });
            categoryPanel.add(btn);
        }

        difficultyBox.addActionListener(e -> {
            if (selectedCategory[0] != null) {
                String level = (String) difficultyBox.getSelectedItem();
                selectedLevel[0] = level;
                refreshCoursesTable(selectedCategory[0], level, tableModel);
            }
        });

        priceFilterButton.addActionListener(e -> {
            if (selectedCategory[0] == null) {
                JOptionPane.showMessageDialog(null, "Please select a category first!");
                return;
            }

            String minStr = minPriceField.getText().trim();
            String maxStr = maxPriceField.getText().trim();

            double min = minStr.isEmpty() ? 0 : Double.parseDouble(minStr);
            double max = maxStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxStr);

            tableModel.setRowCount(0);
            for (Object[] row : CourseDAO.getCoursesByCategoryAndLevelAndPrice(
                    selectedCategory[0], selectedLevel[0], min, max)) {
                tableModel.addRow(row);
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(categoryPanel, BorderLayout.NORTH);
        topPanel.add(difficultyBox, BorderLayout.CENTER);
        topPanel.add(filterPanel, BorderLayout.SOUTH);
        
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(categoryLabel, BorderLayout.NORTH);
        middlePanel.add(tableScroll, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(middlePanel, BorderLayout.CENTER);
        return panel;
    }
// for refreshing the courses
    private void refreshCoursesTable(String category, String level, DefaultTableModel model) {
        model.setRowCount(0);
        for (Object[] course : CourseDAO.getCoursesByCategoryAndLevel(category, level)) {
            model.addRow(course);
        }
    }

    static class UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "739fhdd7m8"; // 🔐 use env var in production

    public static boolean userExists(String email) {
        String query = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createUser(String name, String email, String password) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("⚠️ Email already exists.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static UserAccount authenticate(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {//this prepares the sql statement from the query string 
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();// execute the said query
            if (rs.next()) {
                return new UserAccount(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//the learning tracker thing
    static class LearningTrackerDAO {
        private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
        private static final String USER = "root";
        private static final String PASSWORD = "739fhdd7m8";

        public static List<Object[]> getSkillsByUser(int userId) {
            List<Object[]> skills = new ArrayList<>();
            String query = "SELECT skill, proficiency, hours_studied, last_updated FROM learning_tracker WHERE user_id = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {//this prepares the sql statement from the query string 
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    skills.add(new Object[]{
                        rs.getString("skill"),
                        rs.getString("proficiency"),
                        rs.getInt("hours_studied"),
                        rs.getTimestamp("last_updated")
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return skills;
        }
// this is the adding skills 
        public static boolean addSkill(int userId, String skill, String proficiency) {
            String query = "INSERT INTO learning_tracker (user_id, skill, proficiency, hours_studied) VALUES (?, ?, ?, 0)";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {//this prepares the sql statement from the query string 
                stmt.setInt(1, userId);
                stmt.setString(2, skill);
                stmt.setString(3, proficiency);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
// this helps into updating the skill
        public static boolean updateSkill(int userId, String skill, String proficiency, int hoursStudied) {
            String query = "UPDATE learning_tracker SET proficiency = ?, hours_studied = ? WHERE user_id = ? AND skill = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, proficiency);
                stmt.setInt(2, hoursStudied);
                stmt.setInt(3, userId);
                stmt.setString(4, skill);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    
 // CourseDAO class
    public static class CourseDAO {
        private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
        private static final String USER = "root";
        private static final String PASSWORD = "739fhdd7m8";

        public static List<Object[]> getCoursesByCategoryAndLevelAndPrice(String category, String level, double minPrice, double maxPrice) {
            List<Object[]> courses = new ArrayList<>();
            String query = "SELECT course_title, price, content_duration, url FROM udemy_courses_cleaned " +
                           "WHERE TRIM(LOWER(category)) = ? AND TRIM(LOWER(course_level)) LIKE ? AND price BETWEEN ? AND ?";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {//this prepares the sql statement from the query string 

                stmt.setString(1, category.toLowerCase().trim());
                stmt.setString(2, level.equalsIgnoreCase("All Levels") ? "%" : "%" + level.toLowerCase().trim() + "%");// % is used for the sql to search the data
                stmt.setDouble(3, minPrice);
                stmt.setDouble(4, maxPrice);

                ResultSet rs = stmt.executeQuery();// it executes the query
                while (rs.next()) {
                    courses.add(new Object[]{
                        rs.getString("course_title"),
                        rs.getDouble("price") == 0.0 ? "Free" : "$" + rs.getDouble("price"),
                        rs.getDouble("content_duration") + " hrs",
                        rs.getString("url")
                    });
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return courses;
        }
// the fetches the courses data from the database
    public static List<Object[]> getCoursesByCategoryAndLevel(String category, String level) {
    List<Object[]> list = new ArrayList<>();
    String query = "SELECT course_title, price, content_duration, url FROM udemy_courses WHERE TRIM(LOWER(category)) = ?";
    
    if (!level.equalsIgnoreCase("All Levels")) { // this is for users who does not select the all levels
        query += " AND TRIM(LOWER(course_level)) LIKE ?";
    }

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {
    	
        stmt.setString(1, category.toLowerCase().trim());
        if (!level.equalsIgnoreCase("All Levels")) {
            stmt.setString(2, "%" + level.toLowerCase().trim() + "%"); // % is used for the sql to search the data
        }
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("course_title"),
                rs.getDouble("price") == 0.0 ? "Free" : "$" + rs.getDouble("price"),
                rs.getDouble("content_duration") + " hrs",
                rs.getString("url")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}
        
        
        //fr the exam parts
        static class ExamDAO {
            private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
            private static final String USER = "root";
            private static final String PASSWORD = "739fhdd7m8";

            public static List<MockQuestion> getMockQuestions(String examName) {
                List<MockQuestion> questions = new ArrayList<>();
                String query = "SELECT question, option_a, option_b, option_c, option_d, correct_option FROM mock_questions WHERE LOWER(exam_name) = ?";
                System.out.println("Fetching mock questions for: " + examName); // for testing

                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, examName.trim().toLowerCase());
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {//it executes the query and sends the result line by line
                        questions.add(new MockQuestion(
                            rs.getString("question"),
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d"),
                            rs.getString("correct_option").charAt(0)
                        ));
                        System.out.println("Fetched MCQ: " + rs.getString("question")); //testing
                    }
                    System.out.println("Questions found: " + questions.size()); // for testing
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return questions;
            }

//fetching exam dates into the exam panel
            public static String getExamDates(String examName) {
                String query = "SELECT application_date, exam_date, result_date FROM exam_dates WHERE exam_name = ?";
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, examName);
                    ResultSet rs = stmt.executeQuery();// it executes the query
                    if (rs.next()) { // checks if there is a next line here if yes then it fills it with the data
                        return "Application: " + rs.getString("application_date") +
                             "\nExam Date: " + rs.getString("exam_date") +
                             "\nResult: " + rs.getString("result_date");
                    } else {
                        return "Dates not available for " + examName;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "\u26A0\uFE0F Error fetching dates.";// the weird string is for an danger emoji
                }
            }
// this is where save scores into the database
            public static void saveScore(String examName, String email, int score) {
                String query = "INSERT INTO exam_scores (exam_name, user_email, score, taken_at)"
                		+ " VALUES (?, ?, ?, NOW())";
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);//opening a connection to the database thru user , password.
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, examName);
                    stmt.setString(2, email);
                    stmt.setInt(3, score);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
//this fetches the leader board data from the database
            public static List<String[]> getLeaderboard(String examName) {
                List<String[]> leaderboard = new ArrayList<>();
                String query = """
                    SELECT user_email, MAX(score) as top_score
                    FROM exam_scores
                    WHERE exam_name = ?
                    GROUP BY user_email
                    ORDER BY top_score DESC
                    LIMIT 5
                """;

                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);//opening a connection to the database thru user , password.
                     PreparedStatement stmt = conn.prepareStatement(query)) { //this prepares the sql statement from the query string 

                    stmt.setString(1, examName);//preventing sql injection

                    try (ResultSet rs = stmt.executeQuery()) { //this executes the query abd send it to resultSet
                        while (rs.next()) {
                            leaderboard.add(new String[]{
                                rs.getString("user_email"),
                                String.valueOf(rs.getInt("top_score"))
                            });
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return leaderboard;
            }
            // we are using this to update the user in edit profile
            public static boolean updateUser(int userId, String newName, String newEmail, String newPassword) {
                String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, newName);//this name replaces the ?  in the query
                    stmt.setString(2, newEmail);
                    stmt.setString(3, newPassword);
                    stmt.setInt(4, userId);
                    return stmt.executeUpdate() > 0;// here we execute the update
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            }
    
    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new PortfolioManagementSystem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    }
    }
