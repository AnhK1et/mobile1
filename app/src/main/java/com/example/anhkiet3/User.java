package com.example.anhkiet3;
import java.io.Serializable;
public class User implements Serializable {
    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private String phone;
    private String image;
    public static class Name implements Serializable {
        public String firstname;
        public String lastname;
    }
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Name getName() { return name; }
    public String getPhone() { return phone; }
    public String getImage() { return image; }
} 