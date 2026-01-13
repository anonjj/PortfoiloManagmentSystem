package com.portfolio.model;

/**
 * Represents a mock exam question with multiple choice options.
 */
public class MockQuestion {
    public String question;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public char correctOption;

    public MockQuestion(String question, String optionA, String optionB, String optionC, String optionD,
            char correctOption) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }
}
