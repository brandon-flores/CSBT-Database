package com.cashier.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class FXMLTransactionsController implements Initializable {

    @FXML
    private Text cashierUserText;

    @FXML
    private JFXButton transactLogoutButton;

    @FXML
    private TableView<FeeTable> transactionsTable;

    @FXML
    private TableColumn<FeeTable, String> transactDate;

    @FXML
    private TableColumn<FeeTable, String> transactTime;

    @FXML
    private TableColumn<FeeTable, String> transactOR;

    @FXML
    private TableColumn<FeeTable, String> transactPlateNum;

    @FXML
    private TableColumn<FeeTable, String> transactFee;

    @FXML
    private TableColumn<FeeTable, String> transactAmount;

    @FXML
    private TableColumn<FeeTable, String> transactStatus;

    @FXML
    private TextField transactQuantityAF;

    @FXML
    private TextField transactQuantityLF;

    @FXML
    private TextField transactAmountAF;

    @FXML
    private TextField transactAmountLF;

    @FXML
    private TextField transactTotalRevenue;

    @FXML
    private DatePicker transactDateFrom;

    @FXML
    private DatePicker transactDateTo;

    @FXML
    private JFXButton transactBackButton;

    private String currentLogin;
    private ObservableList<FeeTable> fees;
    private DatabaseReference database;
    private int arrival, load;

    @FXML
    void transactLogoutButtonPressed(ActionEvent event) {

    }

    //accepts local date
    private void updateTable(LocalDate startDate, LocalDate endDate){
        arrival = 0;
        load = 0;
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
                                if(fee.getPaidArrival()) arrival+=1;
                                if(fee.getPaidLoading()) {
                                    System.out.println(fee.getOrNum());
                                    load +=1;
                                }
                            }
                            DatabaseReference bref = database.child("Buses").child(fee.getBus_plate());
                            bref.addListenerForSingleValueEvent(new ValueEventListener() { //functions just the same sa listener above pero lain lang reference (instead of Fees, Buses na na table)
                                @Override
                                public void onDataChange(DataSnapshot bussnapshot) {
                                    Bus bus = bussnapshot.getValue(Bus.class);
                                    //System.out.println(fee.getBus_plate());
                                    if(fee.getEmployeeID().equals(currentLogin)){
                                        fees.add(new FeeTable(fee,bus));
                                        transactionsTable.setItems(fees);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                            int productArrival = arrival * 50;
                            int productLoad = load * 150;
                            transactQuantityAF.setText(arrival + "");
                            transactQuantityLF.setText(load + "");
                            transactAmountAF.setText("" + productArrival);
                            transactAmountLF.setText("" + productLoad);
                            transactTotalRevenue.setText("" + (productArrival + productLoad));
                        }
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
                        if (item.isAfter(transactDateTo.getValue())) {
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
        transactDateFrom.setDayCellFactory(dayCellFactory);
        try{
            transactDateTo.getValue();
            updateTable(transactDateFrom.getValue(), transactDateTo.getValue());
        }catch(NullPointerException e){
            updateTable(LocalDate.of(1990, Month.JANUARY, 1), transactDateTo.getValue());
        }

    }

    public void dateStartDateUpdated() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        if (item.isBefore(transactDateFrom.getValue())) {
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
            transactDateFrom.getValue();
            updateTable(transactDateFrom.getValue(), transactDateTo.getValue());
        }catch(NullPointerException e){
            updateTable(transactDateFrom.getValue(), LocalDate.now());
        }
    }

    @FXML
    void transactBackButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentLogin = SingletonLogin.getInstance().getCurrentLogin();
        cashierUserText.setText(SingletonLogin.getInstance().getCurrentLogin());
        transactQuantityAF.setText("9");
        transactQuantityLF.setText("9");
        transactAmountAF.setText("450");
        transactAmountLF.setText("1050");
        transactTotalRevenue.setText("1600");

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
        transactDateFrom.setDayCellFactory(dayCellFactory);
        transactDateTo.setDayCellFactory(dayCellFactory);
        transactDateFrom.setEditable(false);
        transactDateTo.setEditable(false);

        transactDate.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("date"));
        transactTime.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("timePaid"));
        transactOR.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("orNum"));
        transactFee.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("feeType"));
        transactAmount.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("totalAmount"));
        transactStatus.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("voidStatus"));
        transactPlateNum.setCellValueFactory(new PropertyValueFactory<FeeTable, String>("plateNo"));

        database = FirebaseDatabase.getInstance().getReference();
        fees = FXCollections.observableArrayList();
        updateTable(LocalDate.of(1990, Month.JANUARY, 1), LocalDate.now());
    }
}
