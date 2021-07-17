package com.cashier.ui;

import com.database.Bus;
import com.database.Fee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLVoidRequestWindowController implements Initializable {
    //jimro
    @FXML
    private JFXButton cancel;

    @FXML
    private TextField busCompTextField1;

    @FXML
    private TextField DateTimeTextField1;

    @FXML
    private TextField arrivFeeTextField;

    @FXML
    private TextField plateTextField;

    @FXML
    private TextField loadFeeTextField;

    @FXML
    private TextField orNoTextField;

    @FXML
    private TextField totalTextField;

    private Fee fee;
    private Bus bus;
    private String orNo;
    private DatabaseReference database;

    @FXML
    void cancelPressed(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void sendVoidPressed(ActionEvent event) {
        //INSERT what do
        DatabaseReference ref = database.child("Fees").child(orNo);
        Map<String, Object> voidUpdate = new HashMap<>();
        voidUpdate.put("_void", true);

        ref.updateChildren(voidUpdate);

        Stage stage = (Stage) cancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        orNo = SingletonVoid.getInstance().getOrNo();

        DatabaseReference ref = database.child("Fees");
        ref.child(orNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                fee = snapshot.getValue(Fee.class);
                DatabaseReference bref = database.child("Buses");
                bref.child(fee.getBus_plate()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        bus = snapshot.getValue(Bus.class);
                        busCompTextField1.setText(bus.getCompany());
                        DateTimeTextField1.setText(fee.getDatePaid() + "    " + fee.getTimePaid());
                        arrivFeeTextField.setText(fee.getArrivalFee());
                        plateTextField.setText(fee.getBus_plate());
                        loadFeeTextField.setText(fee.getLoadingFee());
                        orNoTextField.setText(fee.getOrNum());
                        totalTextField.setText("" + (Integer.valueOf(fee.getArrivalFee()) + Integer.valueOf(fee.getLoadingFee())));
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }
}
