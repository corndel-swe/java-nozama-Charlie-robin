package com.corndel.nozama.models;


import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

public class Auth {

    public static Auth of(Context context) throws ValidationException {
        return context.bodyValidator(Auth.class)
                .check((auth) -> auth.getUsername() != null && !auth.getUsername().isBlank(), "Username can not be null, or Empty")
                .check((user) -> user.getPassword() != null && !user.getPassword().isBlank(), "Password can not be null, or Empty")
                .get();
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
