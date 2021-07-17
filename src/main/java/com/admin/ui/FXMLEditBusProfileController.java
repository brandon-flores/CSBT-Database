package com.admin.ui;

import com.database.Bus;
import com.database.Employee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FXMLEditBusProfileController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton delete;

    @FXML
    private TextField contactPerson;

    @FXML
    private TextField contactNumber;

    @FXML
    private TextField franchise;

    @FXML
    private TextField plateNo;

    @FXML
    private TextField route;

    @FXML
    private TextField type;

    @FXML
    private TextField size;

    @FXML
    private TextField rfid;

    @FXML
    private Label contactPersonErr;

    @FXML
    private Label contactNumberErr;

    @FXML
    private Label franchiseErr;

    @FXML
    private Label plateNoErr;

    @FXML
    private Label routeErr;

    @FXML
    private Label typeErr;

    @FXML
    private Label sizeErr;

    @FXML
    private Label rfidErr;


    private Bus busToEdit;
    private DatabaseReference database;

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private ArrayList<Bus> buses = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        busToEdit = SingletonEditBus.getInstance().getBus();
        getExistingBuses();

        contactPerson.setText(busToEdit.getContactPerson());
        contactNumber.setText(busToEdit.getContactNumber());
        franchise.setText(busToEdit.getCompany());
        plateNo.setText(busToEdit.getPlateNo());
        route.setText(busToEdit.getBusRoute());
        type.setText(busToEdit.getBusType());
        size.setText(busToEdit.getBusSize());
        rfid.setText(busToEdit.getRfid());
    }
    
    private void getExistingBuses(){
        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Bus bus = snap.getValue(Bus.class);
                    buses.add(bus);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
    
    @FXML
    void cancelButtonPressed(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteButtonPressed(ActionEvent event) {
        String contactPersonText = contactPerson.getText();
        String contactNumberText = contactNumber.getText();
        String franchiseText = franchise.getText();
        String plateNoText = plateNo.getText();
        String routeText = route.getText();
        String typeText = type.getText();
        String sizeText = size.getText();
        String RFIDText = rfid.getText();

        if(contactNumberText.equals("") || contactPersonText.equals("") || franchiseText.equals("") ||
                plateNoText.equals("") || routeText.equals("") || typeText.equals("") || sizeText.equals("") ||
                RFIDText.equals("")) {
            alert.setTitle("INCOMPLETE DATA");
            alert.setHeaderText("");
            alert.setContentText("Please fill in all the data needed.");
            alert.showAndWait();
        }else{
            DatabaseReference ref = database.child("Buses");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Map<String, Object> newEmployee = new HashMap<>();
                    newEmployee.put("activeBus", true);
                    ref.child(plateNoText).updateChildren(newEmployee);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            Stage stage = (Stage) delete.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void editButtonPressed(ActionEvent event) {
        String contactPersonText = contactPerson.getText();
        String contactNumberText = contactNumber.getText();
        String franchiseText = franchise.getText().toUpperCase();
        String plateNoText = plateNo.getText().toUpperCase();
        String routeText = route.getText().toUpperCase();
        String typeText = type.getText().toUpperCase();
        String sizeText = size.getText().toUpperCase();
        String RFIDText = rfid.getText();
        Boolean error = false;
        if(contactNumberText.equals("") || contactPersonText.equals("") || franchiseText.equals("") ||
                plateNoText.equals("") || routeText.equals("") || typeText.equals("") || sizeText.equals("") ||
                RFIDText.equals("")) {
            error = true;
            alert.setTitle("INCOMPLETE DATA");
            alert.setHeaderText("");
            alert.setContentText("Please fill in all the data needed.");
            alert.showAndWait();
        }else{
            if(!plateNoText.equals("") && plateNoText != null){
                if(!plateNoText.equals(busToEdit.getPlateNo())){
                    for(Bus b : buses){
                        if(b.getPlateNo().equals(plateNoText)){
                            error = true;
                            alert.setTitle("PLATE NUMBER ALREADY EXISTS");
                            alert.setHeaderText("");
                            alert.setContentText("Please input another plate number.");
                            alert.showAndWait();
                        }
                    }
                }
            }
            if(!RFIDText.equals("") && RFIDText != null){
                if(!RFIDText.equals(busToEdit.getRfid())){
                    for(Bus b : buses){
                        if(b.getRfid().equals(RFIDText)){
                            error = true;
                            alert.setTitle("RFID already exists.");
                            alert.setHeaderText("");
                            alert.setContentText("Please input another value.");
                            alert.showAndWait();
                        }
                    }
                }
            }
        }

        if(!error){
            DatabaseReference ref = database.child("Buses");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(plateNoText.equals(busToEdit.getPlateNo())){
                        Map<String, Object> newBus = new HashMap<>();
                        newBus.put("plateNo", plateNoText);
                        newBus.put("company", franchiseText);
                        newBus.put("contactPerson", contactPersonText);
                        newBus.put("contactNumber", contactNumberText);
                        newBus.put("busType", typeText);
                        newBus.put("busSize", sizeText);
                        newBus.put("busRoute", routeText);
                        newBus.put("rfid",RFIDText);

                        ref.child(plateNoText).updateChildren(newBus);
                    }else {
                        if(snapshot.hasChild(plateNoText)){
                            //todo throw user exist
                            // done above
                        }else {
                            System.out.println("hello");
                            boolean isMinibus = false;
                            if(sizeText.equals("MINIBUS")){
                                isMinibus = true;
                            }
                            Bus e = new Bus("",sizeText, franchiseText, isMinibus, plateNoText,contactPersonText,
                                    contactNumberText, typeText, routeText, "","",true, RFIDText);
                            ref.child(busToEdit.getPlateNo()).setValue(null);
                            ref.child(plateNoText).setValue(e);
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {}
            });
            // closes the window
            Stage stage = (Stage) editButton.getScene().getWindow();
            stage.close();
        }
    }
}
