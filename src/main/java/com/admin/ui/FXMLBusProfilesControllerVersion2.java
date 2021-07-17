package com.admin.ui;

import com.database.Bus;
import com.database.Fee;
import com.database.FeeTable;
import com.google.firebase.database.*;
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

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLBusProfilesControllerVersion2 implements Initializable {
    Stage anotherStage = new Stage();
    @FXML
    private Text adminUserText;

    @FXML
    private ComboBox search;

    @FXML
    private TableView<Bus> transactionsTable;

    @FXML
    private TableColumn<Bus, String> franchise;

    @FXML
    private TableColumn<Bus, String> contactPerson;

    @FXML
    private TableColumn<Bus, String> contactNumber;

    @FXML
    private TableColumn<Bus, String> plateNumber;

    @FXML
    private TableColumn<Bus, String> size;

    @FXML
    private TableColumn<Bus, String> route;

    @FXML
    private TableColumn<Bus, String> type;

    private DatabaseReference database;
    private ObservableList<Bus> buses;

    private void displayAll(){
        DatabaseReference ref = database.child("Buses");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Bus bus = snap.getValue(Bus.class);
                    buses.add(bus);
                    transactionsTable.setItems(buses);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @FXML
    void busCreateButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        busViewButtonPressed(event);
        anotherStage.show();
    }

    @FXML
    void busEditButtonPressed(ActionEvent event) throws IOException {
        if(transactionsTable.getSelectionModel().getSelectedCells().isEmpty()){
            System.out.println("no bus selected!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No bus selected.");
            alert.setContentText("Please select a bus profile to edit.");
            alert.showAndWait();
        }else{
            Bus editBus = transactionsTable.getSelectionModel().getSelectedItem();
            SingletonEditBus.getInstance().setBus(editBus);
            FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditBusProfile.fxml"));
            Parent anotherRoot = anotherLoader.load();
            //anotherStage.centerOnScreen();  //does not really work idk
            Scene anotherScene = new Scene(anotherRoot);
            anotherStage.setScene(anotherScene);
            anotherStage.initStyle(StageStyle.UNDECORATED);

            busViewButtonPressed(event);
            anotherStage.show();
        }
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
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        busViewButtonPressed(event);
        anotherStage.show();
    }

    @FXML
    void employeeEditButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditEmployee.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        busViewButtonPressed(event);
        anotherStage.show();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminUserText.setText("Sir Joey");
        /**
         *  TODO: implement search text field
         */
        franchise.setCellValueFactory(new PropertyValueFactory<Bus, String>("company"));
        contactPerson.setCellValueFactory(new PropertyValueFactory<Bus, String>("contactPerson"));
        contactNumber.setCellValueFactory(new PropertyValueFactory<Bus, String>("contactNumber"));
        plateNumber.setCellValueFactory(new PropertyValueFactory<Bus, String>("plateNo"));
        size.setCellValueFactory(new PropertyValueFactory<Bus, String>("busSize"));
        route.setCellValueFactory(new PropertyValueFactory<Bus, String>("busRoute"));
        type.setCellValueFactory(new PropertyValueFactory<Bus, String>("busType"));
        database = FirebaseDatabase.getInstance().getReference();
        buses = FXCollections.observableArrayList();

        search.getItems().addAll(
                "SEARCH by: ACTIVE",
                "Search by: INACTIVE",
                "Search by: CASHIER",
                "Search by: ADMIN"
        );

        displayAll();
//        search.getText();
    }
}