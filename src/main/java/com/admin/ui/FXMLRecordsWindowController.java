package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class FXMLRecordsWindowController implements Initializable {
    Stage anotherStage = new Stage();
    @FXML
    private Text adminUserText;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private ComboBox search;

    @FXML
    private JFXButton currentButton;

    @FXML
    private JFXButton recordsButton;

    @FXML
    private JFXButton employeeViewButton;

    @FXML
    private JFXButton employeeCreateButton;

    @FXML
    private JFXButton employeeEditButton;

    @FXML
    private JFXButton busViewButton;

    @FXML
    private JFXButton busCreateButton;

    @FXML
    private JFXButton busEditButton;

    @FXML
    private DatePicker dateTo;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private TextField quantityAF;

    @FXML
    private TextField quantityLF;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TextField amountAF;

    @FXML
    private TextField amountLF;

    @FXML
    private TextField totalRevenue;

    @FXML
    private TextField totalVoid;

    @FXML
    private TableView<FeeTable> transactionsTable;

    @FXML
    private TableColumn<FeeTable, String> date;

    @FXML
    private TableColumn<FeeTable, String> time;

    @FXML
    private TableColumn<FeeTable, String> cashier;

    @FXML
    private TableColumn<FeeTable, String> orNo;

    @FXML
    private TableColumn<FeeTable, String> company;

    @FXML
    private TableColumn<FeeTable, String> busType;

    @FXML
    private TableColumn<FeeTable, String> plateNo;

    @FXML
    private TableColumn<FeeTable, String> route;

    @FXML
    private TableColumn<FeeTable, String> feeType;

    @FXML
    private TableColumn<FeeTable, String> amount;

    @FXML
    private TableColumn<FeeTable, String> status;

    private String column;
    private DatabaseReference database;
    private int tarrival, load, totalvoided;
    private ObservableList<FeeTable> fees;
    private LocalDate currentDateFrom, currentDateTo;

    private void updateTable(LocalDate startDate, LocalDate endDate){
        currentDateFrom = startDate;
        currentDateTo = endDate;
        tarrival = 0;
        load = 0;
        totalvoided = 0;
        fees.clear();
        DatabaseReference dref = database.child("Fees");

        /**
         * ordered by date (firebase default method)
         * getting all items starting from the startDate to endDate
         */
        dref.orderByChild("datePaid").startAt(startDate.toString()).endAt(endDate.toString())
                .addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            Fee fee = snap.getValue(Fee.class);
                            if(!fee.get_void()){
                                if(fee.getPaidArrival()) tarrival += 1;
                                if(fee.getPaidLoading()) load +=1;
                            }
                            if(fee.get_void()) totalvoided += 1;

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

                            int productArrival = tarrival * 50;
                            int productLoad = load * 150;
                            quantityAF.setText(tarrival + "");
                            quantityLF.setText(load + "");
                            amountAF.setText("" + productArrival);
                            amountLF.setText("" + productLoad);
                            totalRevenue.setText("" + (productArrival + productLoad));
                            totalVoid.setText("" + totalvoided);
                        }
                        //ObservableList<FeeTable> feeList = transactionsTable.getItems();
//                        for (FeeTable f : fees) {
//                            System.out.println(f.getTotalAmount());
//                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

    }

    public void dateEndDateUpdated() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isAfter(dateTo.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                        if (item.isAfter(LocalDate.now())){
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                    }
                };
            }
        };
        dateFrom.setDayCellFactory(dayCellFactory);
        try{
            dateTo.getValue();
            updateTable(dateFrom.getValue(), dateTo.getValue());
        }catch(NullPointerException e){
            updateTable(LocalDate.of(1990, Month.JANUARY, 1), dateTo.getValue());
        }

    }

    public void dateStartDateUpdated() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isBefore(dateFrom.getValue())) {
                            setDisable(true);
                        }
                        if (item.isAfter(LocalDate.now())){
                            setDisable(true);
                        }
                    }
                };
            }
        };

        try{
            dateFrom.getValue();
            updateTable(dateFrom.getValue(), dateTo.getValue());
        }catch(NullPointerException e){
            updateTable(dateFrom.getValue(), LocalDate.now());
        }
    }

    @FXML
    void busCreateButtonPressed(ActionEvent event) throws IOException {
        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        recordsButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void busEditButtonPressed(ActionEvent event) throws IOException {
        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditBusProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        recordsButtonPressed(event);
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
    void currentButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
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

        recordsButtonPressed(event);
        anotherStage.show();*/
    }

    @FXML
    void employeeEditButtonPressed(ActionEvent event) throws IOException {
        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditEmployee.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        recordsButtonPressed(event);
        anotherStage.show();*/
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
    void logoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
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

    public void initialize(URL url, ResourceBundle rb) {
        currentDateTo = LocalDate.now();
        currentDateFrom = LocalDate.now();
        String currentLogin = SingletonLogin.getInstance().getCurrentLogin();
        adminUserText.setText(currentLogin);
        totalRevenue.setText("1600");
        quantityAF.setText("9");
        quantityLF.setText("9");
        amountAF.setText("450");
        amountLF.setText("1050");
        totalVoid.setText("10");

        /**
         *  TODO: implement search text field
         */
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #b3b5b0;");
                        }
                    }
                };
            }
        };
        dateFrom.setDayCellFactory(dayCellFactory);
        dateTo.setDayCellFactory(dayCellFactory);
        dateFrom.setEditable(false);
        dateTo.setEditable(false);

        date.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("date"));
        time.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("timePaid"));
        orNo.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("orNum"));
        company.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busCompany"));
        busType.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busType"));
        plateNo.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("plateNo"));
        route.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("busRoute"));
        feeType.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("feeType"));
        amount.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("totalAmount"));
        status.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("voidStatus"));
        cashier.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("employeeID"));


        search.getItems().addAll(
                "CLEAR FILTER",
                "TIME",
                "OR no.",
                "COMPANY",
                "BUS TYPE",
                "PLATE no.",
                "ROUTE",
                "FEE TYPE",
                "AMOUNT",
                "CASHIER",
                "STATUS"
        );

        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();

        search.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                fees.clear();

                DatabaseReference ref = database.child("Fees");
                Stage stage = (Stage) search.getScene().getWindow();
                if(search.getItems().get((Integer) number2).equals("TIME")) {
                    column = "timePaid";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("OR no.")) {
                    column = "orNum";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("COMPANY")) {
                    column = "company";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("BUS TYPE")) {
                    column = "busType";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("PLATE no.")) {
                    column = "plateNo";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("ROUTE")) {
                    column = "busRoute";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("FEE TYPE")) {
                    column = "feeType";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("AMOUNT")) {
                    column = "totalAmount";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("CASHIER")) {
                    column = "employeeID";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("STATUS")) {
                    column = "_void";
                    sort();
                } else if(search.getItems().get((Integer) number2).equals("CLEAR FILTER")) {
                    textFieldSearch.setText("");
                    updateTable(LocalDate.of(1990, Month.JANUARY, 1), LocalDate.now());
                }
            }
        });
        updateTable(LocalDate.of(1990, Month.JANUARY, 1), LocalDate.now());
    }

    private void sort(){
        fees.clear();
        String input = textFieldSearch.getText();
        DatabaseReference ref = database.child("Fees");
        ref.orderByChild("datePaid").startAt(currentDateFrom.toString()).endAt(currentDateTo.toString())
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
                                        case "timePaid":
                                            if(fee.getTimePaid().equals(input)){
                                                flag = true;
                                            }
                                            break;
                                        case "orNum":
                                            if(fee.getOrNum().equals(input)){
                                                flag = true;
                                            }
                                            break;
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
                                        case "feeType":
                                            if(input.equals("AF")){
                                                if(fee.getPaidArrival()){
                                                    flag = true;
                                                }
                                            }
                                            if(input.equals("LF")){
                                                if(fee.getPaidLoading()){
                                                    flag = true;
                                                }
                                            }
                                            if(input.equals("AF, LF")){
                                                if(fee.getPaidLoading() && fee.getPaidArrival()){
                                                    flag = true;
                                                }
                                            }
                                            break;
                                        case "totalAmount":
                                            if(input.equals("50")){
                                                if(fee.getPaidArrival() && !fee.getPaidLoading()){
                                                    flag = true;
                                                }
                                            }
                                            if(input.equals("150")){
                                                if(fee.getPaidLoading() && !fee.getPaidArrival()){
                                                    flag = true;
                                                }
                                            }
                                            if(input.equals("200")){
                                                if(fee.getPaidLoading() && fee.getPaidArrival()){
                                                    flag = true;
                                                }
                                            }
                                            break;
                                        case "employeeID":
                                            if(fee.getEmployeeID().equals(input)){
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
