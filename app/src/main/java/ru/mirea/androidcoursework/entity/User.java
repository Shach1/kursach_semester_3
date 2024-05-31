package ru.mirea.androidcoursework.entity;

public class User {
    public String id, name, secondName, email;

    public User() {
    }

    public User(String id, String name, String secondName, String email) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.email = email;
    }
}
