package com.admin.ui;

import com.database.Employee;
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

public class FXMLEditEmployeeController implements Initializable {

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private TextField status;

    @FXML
    private JFXButton enter;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton cancel;

    private Employee employeeToEdit;
    //private Employee employee;
    private DatabaseReference database;
    private String userNameText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = FirebaseDatabase.getInstance().getReference();
        employeeToEdit = SingletonEditEmployee.getInstance().getEmployee();

        firstName.setText(employeeToEdit.getFirstName());
        lastName.setText(employeeToEdit.getLastName());
        userName.setText(employeeToEdit.getUsername());
        //password.setText(employeeToEdit.getPassword());
        status.setText(employeeToEdit.getEstatus());
    }

    @FXML
    void cancelPressed(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deletePressed(ActionEvent event){
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        userNameText = userName.getText();
        //String passwordText = password.getText();


        if(firstNameText.equals("") || lastNameText.equals("") || userNameText.equals("")){
            //todo throw error nga empty
        }

        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> newEmployee = new HashMap<>();
                newEmployee.put("estatus", "INACTIVE");
                newEmployee.put("activeEmployee", false);
                ref.child(userNameText).updateChildren(newEmployee);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        Stage stage = (Stage) delete.getScene().getWindow();
        stage.close();
    }

    @FXML
    void enterPressed(ActionEvent event) {
        String firstNameText = firstName.getText();
        String lastNameText = lastName.getText();
        userNameText = userName.getText();
        //String passwordText = password.getText();

//        System.out.println(userNameText);

        if(firstNameText.equals("") || lastNameText.equals("") || userNameText.equals("")){
            //todo throw error nga empty
        }

        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(userNameText.equals(employeeToEdit.getUsername())){
                    Map<String, Object> newEmployee = new HashMap<>();
                    newEmployee.put("firstName", firstNameText);
                    newEmployee.put("lastName", lastNameText);

                    ref.child(userNameText).updateChildren(newEmployee);
                }else {
                    if(snapshot.hasChild(userNameText)){
                        //todo throw user exist
                        //System.out.println("geee");
                    }else {
                        System.out.println("hello");
                        Employee e = new Employee(userNameText,employeeToEdit.getPassword(),firstNameText,lastNameText, employeeToEdit.getWorkType(),employeeToEdit.isActiveEmployee());
                        ref.child(employeeToEdit.getUsername()).setValue(null);
                        ref.child(userNameText).setValue(e);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        // closes the window
        Stage stage = (Stage) enter.getScene().getWindow();
        stage.close();
    }

}
