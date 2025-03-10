package com.nazarov;

public class UserData {
    public String login;
    public String password;

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserData() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
