package com.cashier.ui;

import com.database.Bus;
import com.database.Fee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

//package com.cashier.ui;

/**
 * @author alboresallyssa
 */
public class FXMLArrivalWindowController implements Initializable {

    //These items are for the combo boxes in arrival window
    @FXML
    private ComboBox busFDD;
    @FXML
    private ComboBox minibusFDD;

    //These items are for the check boxes in arrival window
    @FXML
    private CheckBox arrivalFee;
    @FXML
    private CheckBox loadingFee;

    //These items are for the text fields in arrival window
    @FXML
    private TextField busNumber;
    @FXML
    private TextField plateNumber;

    //Items for error checking
    @FXML
    private Label lblBusFranchiseErr;

    @FXML
    private Label lblBusNumberErr;

    @FXML
    private Label lblBusFeeTypeErr;

    @FXML
    private Label lblMiniBusFranchiseErr;

    @FXML
    private Label lblMiniBusPlateNumberErr;

    @FXML
    private DatabaseReference database;

    //private int currentOrNum;
    private boolean busExists;
    private int ORNUM;

    /**
     * When this method is called, a pop up window will appear.
     * The pop up window is the confirmation window for the BUS.
     *
     * @param event
     * @throws IOException
     */
    public void busConfirmButtonPushed(ActionEvent event) throws IOException, InterruptedException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // idk where ni na use
        LocalDate localDate = LocalDate.now();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateFormat = sdf.format(date.getTime());

        try {
            lblBusFranchiseErr.setText("");
            String typeOfFee = ""; //idk where ni na use
            boolean paidArrival = false;
            boolean paidLoading = false;
            if (arrivalFee.isSelected()) {
                paidArrival = true;
            }
            if (loadingFee.isSelected()) {
                paidLoading = true;
            }

            String busNum = busNumber.getText();

            if (!loadingFee.isSelected() && !arrivalFee.isSelected()) {
                lblBusFeeTypeErr.setText("* - Select Fee to be paid");

            } else {
                lblBusFeeTypeErr.setText("");
                busExists = false;
                final boolean hasArrival = paidArrival;   //inner class calls needs to be final
                final boolean hasLoading = paidLoading;    //same reason as to paid arrival
                String franchiseSelected = busFDD.getValue().toString();
                //String orNumber = "#" + String.valueOf(currentOrNum);


                /**
                 * referenced adto sa fees table
                 * first listener below is to determine unsay pinakalast na OR number naa sa firebase para plus 1 nalang
                 */
                DatabaseReference ref = database.child("Fees");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Long l = snapshot.getChildrenCount();
                        ORNUM = l.intValue() + 1;
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                /**
                 * Listener below check if such Bus exists
                 * having problems sa pag throw sa error  if no bus exist. pero di ra siya mu add ug fee if sayop ang bus,
                 * ang problem kay di lang mugawas ang alert window
                 */
                ref = database.child("Buses");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    //@Override
                    public void onDataChange(DataSnapshot snapshot) {
                        /**
                         * checks if bus number with the given franchise exists
                         */
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Bus bus = snap.getValue(Bus.class);
                            if(bus.getBusNumber().equals(busNum) && bus.getCompany().equals(franchiseSelected)){
                                busExists = true;
                                if(bus.isMiniBus()){ //alerts error since pang bus ni nga transaction, not minibus. musulod siya diri pero na alert will be displayed
//                                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                                    alert.setTitle("INCORRECT DATA");
//                                    alert.setHeaderText("Please check your data");
//                                    alert.setContentText("");
//                                    alert.showAndWait();

                                }
                                else{
                                    Fee forDatabase = new Fee(hasArrival, hasLoading, dateFormat, "" + ORNUM, "Cashier 01", localDate, bus.getPlateNo());
                                    DatabaseReference aref = database.child("Fees");
                                    aref.child(forDatabase.getOrNum()).setValue(forDatabase);
                                }
                            }
                        }
                        if(!busExists) {// alert will not pop out :(

//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setTitle("NO BUS");
//                            alert.setHeaderText("No buses on record");
//                            alert.setContentText("Contact admin");
//
//                            alert.showAndWait();
                            lblBusFranchiseErr.setText("* - No bus on records: Contact Admin");
                        }

                    }
                    public void onCancelled(DatabaseError error) {
                        lblBusFranchiseErr.setText("* - No bus on records: Contact Admin");
                    }
                });
            }
        } catch (NullPointerException e) { //works fine
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("INCOMPLETE DATA");
//            alert.setHeaderText("Please fill in all data.");
//            alert.setContentText("");
//
//            alert.showAndWait();
            lblBusFranchiseErr.setText("* - Please Select Franchise");
        }

        if(busNumber.getText().equals("")) {
            lblBusNumberErr.setText("* - Input bus number");
        } else {
            lblBusNumberErr.setText("");
        }

    }

    /**
     * When this method is called, a pop up window will appear.
     * The pop up window is the confirmation window for the MINIBUS.
     *
     * @param event
     * @throws IOException
     */
    public void minibusConfirmButtonPushed(ActionEvent event) throws IOException, InterruptedException {
        lblMiniBusPlateNumberErr.setText("");
        lblMiniBusFranchiseErr.setText("");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String dateFormat = sdf.format(date.getTime());

        try {
            busExists = false;
            String franchiseSelected = minibusFDD.getValue().toString(); //idk unsay use ani nga naa namay plate number which is unique
            String plateNum1 = plateNumber.getText();
            // autocaps the plate number inputted
            String plateNum = plateNum1.toUpperCase();

            /**
             * Sme with bus, determines the OR number
             */
            DatabaseReference ref = database.child("Fees");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Long l = snapshot.getChildrenCount();
                    ORNUM = l.intValue() + 1;
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            /**
             * works same with BusConfirmButton
             */
            ref = database.child("Buses");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                ArrayList<Fee> feeslist = new ArrayList<>();
                //@Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.hasChild(plateNum1)) {
                        DatabaseReference busRef = database.child("Buses").child(plateNum1);
                        busRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot bussnapshot) {
                                Bus bus = bussnapshot.getValue(Bus.class);
                                if(bus.isMiniBus() && bus.getCompany().equals(franchiseSelected)) { //true since minibus na ni nga button
                                    System.out.println("nisulod ko diri");
                                    busExists = true;
                                    String orNumber = "#" + String.valueOf(ORNUM);
                                    Fee forDatabase = new Fee(true, false, dateFormat, "" + ORNUM, "Cashier 01", localDate, plateNum);
                                    DatabaseReference aref = database.child("Fees");
                                    aref.child(forDatabase.getOrNum()).setValue(forDatabase);
                                }
                                else { //same problem, goes here but no alert will be displayed
//                                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                                    alert.setTitle("INCORRECT DATA");
//                                    alert.setHeaderText("Check input");
//                                    alert.setContentText("");
//
//                                    alert.showAndWait();
                                    lblMiniBusFranchiseErr.setText("* - Incorrect Data");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });

                    }
                    else{//alert will not be displayed
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("NO BUS");
//                        alert.setHeaderText("No buses on record");
//                        alert.setContentText("Please contact the admin.");
//
//                        alert.showAndWait();
                        lblMiniBusFranchiseErr.setText("* - No bus on record");
                    }

                }
                public void onCancelled(DatabaseError error) {

                }
            });
        }catch(NullPointerException e) { //sad. 2nd alert that works
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("INCOMPLETE DATA");
//            alert.setHeaderText("Please fill in all data.");
//            alert.setContentText(" ");
//
//            alert.showAndWait();

            lblMiniBusFranchiseErr.setText("* - Incomplete Data");
        }
    }


    @FXML
    void arrivalWindowCashierPressed(ActionEvent event) { //already commented out
        /*Parent tableViewParent = FXMLLoader.load(getClass().getResource(""));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();*/
    }

    @FXML
    void arrivalWindowLogoutPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLLoginFormWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void arrivalWindowTransactPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLArrivalWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void arrivalWindowVoidPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLVoidWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Sets the error labels to blank
         */
        lblBusFeeTypeErr.setText("");
        lblBusFranchiseErr.setText("");
        lblBusNumberErr.setText("");
        lblMiniBusFranchiseErr.setText("");
        lblMiniBusPlateNumberErr.setText("");

        /**
         * These items are for configuring the Combo Box.
         * BUS Combo Box
         */

        busFDD.getItems().add("CERES LINER");
        busFDD.getItems().addAll("SUNRAYS", "SOCORRO", "METROLINK");
        busFDD.setVisibleRowCount(3);
        busFDD.setEditable(true);
        busFDD.setPromptText("BUS");

        /**
         * These items are for configuring the Combo Box.
         * MINIBUS Combo Box
         */

        minibusFDD.getItems().add("CERES LINER");
        minibusFDD.getItems().addAll("JEGANS", "CALVO", "COROMINAS", "GABE TRANSIT", "CANONEO", "JHADE");
        minibusFDD.setVisibleRowCount(6);
        minibusFDD.setEditable(true);
        minibusFDD.setPromptText("MINIBUS");


        //TODO DELETE THIS AFTER DATABASE IS DONE
        //database = Database.database;
        database = FirebaseDatabase.getInstance().getReference();
        busExists = false;
    }
}
