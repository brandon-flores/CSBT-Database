package com.database;

public class RangeOR {
    private int start, end;
    private int current;

    public RangeOR(){}

    public RangeOR(String start, String end, String current){
        this.start = Integer.parseInt(start);
        this.end = Integer.parseInt(end);
        this.current = Integer.parseInt(current);
    }

    public RangeOR(int start, int end){
        this.start = start;
        this.end = end;
        current = start;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
