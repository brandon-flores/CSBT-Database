package com.database;

public class Exit {
    private String rfid;
    private String status;
    private String timestamp;

    public Exit(String rfid, String status, String timestamp) {
        this.rfid = rfid;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Exit() {
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
