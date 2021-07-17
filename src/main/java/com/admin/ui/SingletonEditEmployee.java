package com.admin.ui;

import com.database.Employee;

public class SingletonEditEmployee {
    private static SingletonEditEmployee instance = new SingletonEditEmployee();
    public static SingletonEditEmployee getInstance(){
        return instance;
    }

    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
