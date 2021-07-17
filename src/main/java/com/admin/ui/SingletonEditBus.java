package com.admin.ui;

import com.database.Bus;

public class SingletonEditBus {
    private static SingletonEditBus instance = new SingletonEditBus();
    public static SingletonEditBus getInstance(){
        return instance;
    }

    private Bus bus;

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
}
