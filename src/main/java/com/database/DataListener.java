package com.database;

import java.util.ArrayList;

public interface DataListener {
    void newDataReceived(ArrayList<Fee> feeslist);
    //void dataReceived(ArrayList<Bus> buslist);
}
