package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class FXMLCurrentWindowController implements Initializable {
    Stage anotherStage = new Stage();
    @FXML
    private Text adminUserText;

    @FXML
    private TextField dailyRevenue;

    @FXML
    private TextField monthlyRevenue;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TableView<FeeTable> transactionsTable;

    @FXML
    private TableColumn<FeeTable, String> company;

    @FXML
    private TableColumn<FeeTable, String> busType;

    @FXML
    private TableColumn<FeeTable, String> plateNumber;

    @FXML
    private TableColumn<FeeTable, String> route;

    @FXML
    private TableColumn<FeeTable, String> status;

    @FXML
    private ComboBox search;

    private String column;
    private DatabaseReference database;
    private int tarrival, load, totalvoided;
    private int totalRevenue;
    private ObservableList<FeeTable> fees;

    @FXML
    void currentButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void recordsButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLRecordsWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void employeeViewButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLViewAccounts.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void employeeCreateButtonPressed(ActionEvent event) throws IOException {
        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        currentButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void employeeEditButtonPressed(ActionEvent event) throws IOException {
        /**
         *  GET SELECTED ROW THEN SET TEXT ANG SA EDIT BUS PROFILE
         *  THEN GET TEXT
         */

        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditEmployee.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        currentButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void busViewButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLBusProfiles.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void busCreateButtonPressed(ActionEvent event) throws IOException {
       /* FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        currentButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void busEditButtonPressed(ActionEvent event) throws IOException {
        /**
         *  GET SELECTED ROW THEN SET TEXT ANG SA EDIT BUS PROFILE
         *  THEN GET TEXT
         */

        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditBusProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        currentButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void logoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        String currentLogin = SingletonLogin.getInstance().getCurrentLogin();
        adminUserText.setText(currentLogin);
        dailyRevenue.setText("1,600");
        monthlyRevenue.setText("21,600");

        search.getItems().addAll(
                "CLEAR FILTER",
                "COMPANY",
                "BUS TYPE",
                "PLATE #",
                "ROUTE",
                "STATUS"
        );

        company.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busCompany"));
        busType.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busType"));
        plateNumber.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("plateNo"));
        route.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busRoute"));
        status.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("voidStatus"));


        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();
        updateTable();

        search.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                fees.clear();

                DatabaseReference ref = database.child("Fees");
                Stage stage = (Stage) search.getScene().getWindow();
                if(search.getItems().get((Integer) number2).equals("COMPANY")) {
                    column = "company";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("BUS TYPE")) {
                    column = "busType";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("PLATE #")) {
                    column = "plateNo";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("ROUTE")) {
                    column = "busRoute";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("STATUS")) {
                    column = "true";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("CLEAR FILTER")) {
                    textFieldSearch.setText("");
                    updateTable();
                }
            }
        });
    }

    private void updateTable() {
        fees.clear();
        String dateToday = LocalDate.now().toString();
        LocalDate monthBegin = LocalDate.now().withDayOfMonth(1);
        LocalDate monthEnd = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
        DatabaseReference ref = database.child("Fees");

        ref.orderByChild("datePaid").startAt(monthBegin.toString()).endAt(monthEnd.toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        tarrival = 0;
                        load = 0;
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Fee fee = snap.getValue(Fee.class);
                            if(!fee.get_void()){
                                if(fee.getPaidArrival()) tarrival += 1;
                                if(fee.getPaidLoading()) load +=1;
                            }
                        }
                        int productArrival = tarrival * 50;
                        int productLoad = load * 150;
                        totalRevenue = productArrival + productLoad;
                        monthlyRevenue.setText("" + totalRevenue);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
        /**
         * ordered by date (firebase default method)
         * getting all items starting from the startDate to endDate
         */
        ref.orderByChild("datePaid").equalTo(dateToday)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tarrival = 0;
                load = 0;
                for(DataSnapshot snap : snapshot.getChildren()){
                    Fee fee = snap.getValue(Fee.class);
                    if(fee.getPaidArrival()) tarrival += 1;
                    if(fee.getPaidLoading()) load +=1;
                    DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                    bref.addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                        @Override
                        public void onDataChange(DataSnapshot bussnapshot) {
                            Bus bus = bussnapshot.getValue(Bus.class);
                            //System.out.println(fee.getBus_plate());
                            fees.add(new FeeTable(fee,bus));
                            transactionsTable.setItems(fees);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
                }
                int productArrival = tarrival * 50;
                int productLoad = load * 150;
                totalRevenue = productArrival + productLoad;
                dailyRevenue.setText("" + totalRevenue);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void sort(){
        fees.clear();
        String input = textFieldSearch.getText();
        DatabaseReference ref = database.child("Fees");
        ref.orderByChild("datePaid").equalTo(LocalDate.now().toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            Fee fee = snap.getValue(Fee.class);
                            DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                            bref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    boolean flag = false;
                                    Bus bus = snapshot.getValue(Bus.class);
                                    switch (column){
                                        case "company":
                                            if(bus.getCompany().equals(input)){
                                                flag = true;
                                            }
                                            break;
                                        case "busType":
                                            if(bus.getBusSize().equals(input)){
                                                flag = true;
                                            }
                                            break;
                                        case "plateNo":
                                            if(bus.getPlateNo().equals(input)){
                                                flag = true;
                                            }
                                            break;
                                        case "busRoute":
                                            if(bus.getBusRoute().equals(input)){
                                                flag = true;
                                            }
                                            break;
                                        case "_void":
                                            if(fee.get_void()){
                                                flag = true;
                                            }
                                            break;
                                        default: column = "";
                                            break;
                                    }
                                    if(flag){
                                        fees.add(new FeeTable(fee,bus));
                                        transactionsTable.setItems(fees);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
}
