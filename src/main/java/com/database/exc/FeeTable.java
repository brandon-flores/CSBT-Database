package com.database;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class FeeTable {

    private SimpleStringProperty arrivalFee, loadingFee, timePaid,  orNum, employeeID;
    private SimpleStringProperty busCompany, busType, plateNo, busRoute, busSize;
    private LocalDate date;
    private Bus bus;

    public FeeTable(Fee fee, Bus bus) { //constructor added. because I cant call Firebase functions here,
                                        //I decided adding bus to the constructor instead
        this.arrivalFee = new SimpleStringProperty(fee.getArrivalFee());
        this.loadingFee = new SimpleStringProperty(fee.getLoadingFee());
        this.timePaid = new SimpleStringProperty(fee.getTimePaid());
        this.date = LocalDate.parse(fee.getDatePaid());
        this.orNum = new SimpleStringProperty(fee.getOrNum());
        this.employeeID = new SimpleStringProperty(fee.getEmployeeID());
        this.plateNo = new SimpleStringProperty(fee.getBus_plate());
        this.bus = bus;
        initBus();
//        busCompany = new SimpleStringProperty(bus.getCompany());
//        busType = new SimpleStringProperty(bus.getBusSize());
    }

    public FeeTable(String string){
        this.arrivalFee = new SimpleStringProperty("afee");
        this.loadingFee = new SimpleStringProperty("lfee");
        this.timePaid = new SimpleStringProperty("time");
        this.date = LocalDate.parse(LocalDate.now().toString());
        this.orNum = new SimpleStringProperty("ornum");
        this.employeeID = new SimpleStringProperty("employee");
        this.plateNo = new SimpleStringProperty("plate");
        busCompany = new SimpleStringProperty("company");
        busType = new SimpleStringProperty("bustype");
        busRoute = new SimpleStringProperty("route");
    }

    public FeeTable(Fee fee) {
        this.arrivalFee = new SimpleStringProperty(fee.getArrivalFee());
        this.loadingFee = new SimpleStringProperty(fee.getLoadingFee());
        this.timePaid = new SimpleStringProperty(fee.getTimePaid());
        this.date = LocalDate.parse(fee.getDatePaid());
        this.orNum = new SimpleStringProperty(fee.getOrNum());
        this.employeeID = new SimpleStringProperty(fee.getEmployeeID());
        this.plateNo = new SimpleStringProperty(fee.getBus_plate());
        initBus();
    }

    /**
     * retrieve bus info from plate number
     */
    private void initBus() {
//        Database database = Database.database;
//
//        bus = database.getBus("plateNo", getPlateNo()).get(0);
        busCompany = new SimpleStringProperty(bus.getCompany());
        busType = new SimpleStringProperty(bus.getBusSize());
        busRoute = new SimpleStringProperty(bus.getBusRoute());
        busSize = new SimpleStringProperty(bus.getBusSize());
    }


    /**
     * getters and setters
     */
    public String getArrivalFee() {
        return arrivalFee.get();
    }

    public SimpleStringProperty arrivalFeeProperty() {
        return arrivalFee;
    }

    public void setArrivalFee(String arrivalFee) {
        this.arrivalFee.set(arrivalFee);
    }

    public String getLoadingFee() {
        return loadingFee.get();
    }

    public SimpleStringProperty loadingFeeProperty() {
        return loadingFee;
    }

    public void setLoadingFee(String loadingFee) {
        this.loadingFee.set(loadingFee);
    }

    public String getTimePaid() {
        return timePaid.get();
    }

    public SimpleStringProperty timePaidProperty() {
        return timePaid;
    }

    public void setTimePaid(String timePaid) {
        this.timePaid.set(timePaid);
    }

    public String getOrNum() {
        return orNum.get();
    }

    public SimpleStringProperty orNumProperty() {
        return orNum;
    }

    public void setOrNum(String orNum) {
        this.orNum.set(orNum);
    }

    public String getEmployeeID() {
        return employeeID.get();
    }

    public SimpleStringProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmploeeID(String employeeID) {
        this.employeeID.set(employeeID);
    }

    public String getBusCompany() {
        return busCompany.get();
    }

    public SimpleStringProperty busCompanyProperty() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) {
        this.busCompany.set(busCompany);
    }

    public String getBusType() {
        return busType.get();
    }

    public SimpleStringProperty busTypeProperty() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType.set(busType);
    }

    public String getPlateNo() {
        return plateNo.get();
    }

    public SimpleStringProperty plateNoProperty() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo.set(plateNo);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBusRoute() {
        return busRoute.get();
    }

    public SimpleStringProperty busRouteProperty() {
        return busRoute;
    }

    public void setBusRoute(String busRoute) {
        this.busRoute.set(busRoute);
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getBusSize() {
        return busSize.get();
    }

    public SimpleStringProperty busSizeProperty() {
        return busSize;
    }

    public void setBusSize(String busSize) {
        this.busSize.set(busSize);
    }
}
