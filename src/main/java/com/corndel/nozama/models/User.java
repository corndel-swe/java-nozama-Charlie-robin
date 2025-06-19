package com.corndel.nozama.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public static User of(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("userName"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setAvatar(rs.getString("avatar"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    public static User of(Context context) throws ValidationException {
        return context.bodyValidator(User.class)
                .check((user) -> user.getUsername() != null && !user.getUsername().isBlank(), "Name can not be null, or Empty")
                .check((user) -> user.getFirstName() != null && !user.getFirstName().isBlank(), "Name can not be null, or Empty")
                .check((user) -> user.getLastName() != null && !user.getLastName().isBlank(), "Name can not be null, or Empty")
                .check((user) -> user.getEmail() != null && !user.getEmail().isBlank(), "Name can not be null, or Empty")
                .check((user) -> user.getPassword() != null && !user.getPassword().isBlank(), "Name can not be null, or Empty")
                .get();
    }

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar = "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/730.jpg";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
