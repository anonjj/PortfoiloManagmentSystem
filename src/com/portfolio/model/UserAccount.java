package com.portfolio.model;

/**
 * Represents a user account in the portfolio management system.
 */
public class UserAccount {
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
