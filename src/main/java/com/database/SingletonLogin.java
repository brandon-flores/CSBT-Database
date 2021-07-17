package com.database;

public class SingletonLogin {
    private static SingletonLogin instance = new SingletonLogin();
    public static SingletonLogin getInstance(){
        return instance;
    }

    private String currentLogin;

    public String getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(String currentLogin) {
        this.currentLogin = currentLogin;
    }
}
