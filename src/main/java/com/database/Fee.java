package com.database;
    import java.time.LocalDate;

public class Fee {
    private String bus_plate, employeeID; // we need to connect the fee transaction to the bus payer and the employee doing the transaction
    private String arrivalFee, loadingFee;
    private String timePaid,  orNum, comment;
    private String datePaid;
    //private LocalDate datePaid;
    private Boolean _void, paidArrival, paidLoading;

    public Fee(){}


    public Fee(String bus_plate, String employeeID, String arrivalFee, String loadingFee, String timePaid, String orNum, String comment, String datePaid, Boolean _void, Boolean paidArrival, Boolean paidLoading) {
        this.bus_plate = bus_plate;
        this.employeeID = employeeID;
        this.arrivalFee = arrivalFee;
        this.loadingFee = loadingFee;
        this.timePaid = timePaid;
        this.orNum = orNum;
        this.comment = comment;
        this.datePaid = datePaid;
        this._void = _void;
        this.paidArrival = paidArrival;
        this.paidLoading = paidLoading;
    }

    public Fee(boolean paidArrival, boolean paidLoading, String timePaid, String orNum, String employeeID, LocalDate datePaid, String plateNumber){
        this.paidArrival = paidArrival;
        this.paidLoading = paidLoading;
        initFees();
        this.bus_plate = plateNumber;
        this.timePaid = timePaid;
        this.datePaid = datePaid.toString();
        this.orNum = orNum;
        this.employeeID = employeeID;
        this._void = false;
    }

    /**
     * Initializes bus data
     */


    public void initFees() {
        arrivalFee = paidArrival ? "50" : "0";
        loadingFee = paidLoading ? "150" : "0";
    }

    public String getBus_plate() {
        return bus_plate;
    }

    public void setBus_plate(String bus_plate) {
        this.bus_plate = bus_plate;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getArrivalFee() {
        return arrivalFee;
    }

    public void setArrivalFee(String arrivalFee) {
        this.arrivalFee = arrivalFee;
    }

    public String getLoadingFee() {
        return loadingFee;
    }

    public void setLoadingFee(String loadingFee) {
        this.loadingFee = loadingFee;
    }

    public String getTimePaid() {
        return timePaid;
    }

    public void setTimePaid(String timePaid) {
        this.timePaid = timePaid;
    }

    public String getOrNum() {
        return orNum;
    }

    public void setOrNum(String orNum) {
        this.orNum = orNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public Boolean get_void() {
        return _void;
    }

    public void set_void(Boolean _void) {
        this._void = _void;
    }

    public Boolean getPaidArrival() {
        return paidArrival;
    }

    public void setPaidArrival(Boolean paidArrival) {
        this.paidArrival = paidArrival;
    }

    public Boolean getPaidLoading() {
        return paidLoading;
    }

    public void setPaidLoading(Boolean paidLoading) {
        this.paidLoading = paidLoading;
    }


}
