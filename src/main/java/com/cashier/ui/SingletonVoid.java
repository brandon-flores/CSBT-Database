package com.cashier.ui;

import com.database.Bus;

public class SingletonVoid {
    private static SingletonVoid instance = new SingletonVoid();
    public static SingletonVoid getInstance(){
        return instance;
    }

    private String orNo;

    public String getOrNo() {
        return orNo;
    }

    public void setOrNo(String orNo) {
        this.orNo = orNo;
    }
}
