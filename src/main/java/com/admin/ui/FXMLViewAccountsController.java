package com.admin.ui;

import com.database.Bus;
import com.database.Employee;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLViewAccountsController implements Initializable {
    Stage anotherStage = new Stage();

    @FXML
    private Text adminUserText;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private ComboBox search;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TableView<Employee> transactionsTable;

    @FXML
    private TableColumn<Employee, String> firstName;

    @FXML
    private TableColumn<Employee, String> lastName;

    @FXML
    private TableColumn<Employee, String> username;

    /*@FXML
    private TableColumn<Employee, String> password;*/

    @FXML
    private TableColumn<Employee, String> department;

//    @FXML
//    private TableColumn<Employee, String> shift;

    @FXML
    private TableColumn<Employee, String> status;

    private String column;
    private Employee employeeToEdit;
    private DatabaseReference database;
    private ObservableList<Employee> employees;

    void displayAll(){
        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getChildrenCount());
                for(DataSnapshot snap : snapshot.getChildren()){
                    Employee employee = snap.getValue(Employee.class);
                    //System.out.println(employee.getUsername());
                   // System.out.println(snap.getClass());
                    employees.add(employee);
                    transactionsTable.setItems(employees);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @FXML
    void busCreateButtonPressed(ActionEvent event) throws IOException {
        /*FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateProfile.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        employeeViewButtonPressed(event);
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

        employeeViewButtonPressed(event);
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
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        //anotherStage.centerOnScreen();  //does not really work idk
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.initStyle(StageStyle.UNDECORATED);

        employeeViewButtonPressed(event);
        anotherStage.show();
    }

    @FXML
    void employeeEditButtonPressed(ActionEvent event) throws IOException {
        if(transactionsTable.getSelectionModel().getSelectedCells().isEmpty()){
            System.out.println("no employee selected!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No employee selected.");
            alert.setContentText("Please select an employee to edit.");
            alert.showAndWait();
        }else{
            Employee employee = transactionsTable.getSelectionModel().getSelectedItem();
            SingletonEditEmployee.getInstance().setEmployee(employee);
            System.out.println(SingletonEditEmployee.getInstance().getEmployee().getEstatus());
            FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLEditEmployee.fxml"));
            Parent anotherRoot = anotherLoader.load();
            //anotherStage.centerOnScreen();  //does not really work idk
            Scene anotherScene = new Scene(anotherRoot);
            anotherStage.setScene(anotherScene);
            anotherStage.initStyle(StageStyle.UNDECORATED);

            employeeViewButtonPressed(event);
            anotherStage.show();
        }
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
        /**
         *  TODO: implement search text field
         */
        String currentLogin = SingletonLogin.getInstance().getCurrentLogin();
        adminUserText.setText(currentLogin);


        firstName.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
        username.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        //password.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
        department.setCellValueFactory(new PropertyValueFactory<Employee, String>("workType"));
        status.setCellValueFactory(new PropertyValueFactory<Employee, String>("estatus"));

        database = FirebaseDatabase.getInstance().getReference();
        employees = FXCollections.observableArrayList();

        search.getItems().addAll(
                "CLEAR FILTER",
                "FIRST NAME",
                "LAST NAME",
                "USERNAME",
             //  "PASSWORD",
                "DEPARTMENT",
                "STATUS"
        );

        search.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                employees.clear();

                if(search.getItems().get((Integer) number2).equals("FIRST NAME")) {
                    column = "firstName";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("LAST NAME")) {
                    column = "lastName";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("USERNAME")) {
                    column = "username";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("DEPARTMENT")) {
                    column = "workType";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("STATUS")) {
                    column = "estatus";
                    sort();
                }else if(search.getItems().get((Integer) number2).equals("CLEAR FILTER")) {
                    textFieldSearch.setText("");
                    displayAll();
                }
            }
        });

        displayAll();
        dataListener();
        //search.getText();
    }

    private void sort(){
        String item = textFieldSearch.getText();
        DatabaseReference ref = database.child("Employees");
        ref.orderByChild(column).equalTo(item)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Employee employee = snap.getValue(Employee.class);
                            employees.add(employee);
                            transactionsTable.setItems(employees);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }

    private void dataListener(){
        DatabaseReference ref = database.child("Employees");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
//                buses.clear();
//                displayAll();
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
                employees.clear();
                displayAll();
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
//                buses.clear();
//                displayAll();
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}
