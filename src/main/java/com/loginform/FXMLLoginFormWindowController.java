package com.loginform;

import com.database.Employee;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoginFormWindowController implements Initializable{
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private JFXButton logIn;

    @FXML
    private Label usernameErr;

    @FXML
    private Label passwordErr;

    private DatabaseReference database;
    private ObservableList<Employee> employees;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        employees = FXCollections.observableArrayList();
        database = FirebaseDatabase.getInstance().getReference();

        //todo store password and username
        DatabaseReference ref = database.child("Employees");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    Employee employee = snap.getValue(Employee.class);
                    employees.add(employee);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    public void tabPressedUsername(KeyEvent e) throws IOException {
        KeyCode f = e.getCode();
        if(f.toString().equals("TAB")) {
            passwordTextField.requestFocus();
        }
    }


    @FXML
    public void logInButtonPushed(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();
        usernameErr.setVisible(false);
        passwordErr.setVisible(false);

        boolean flag = false; // it becomes true if it sees the username in the list
        System.out.println(employees.size());
        for (Employee e : employees) {
            if (e.getUsername().equals(user)) {
                if(e.getEstatus().equals("ACTIVE")){
                    flag = true;
                    if (e.getPassword().equals(pass)) {
                        SingletonLogin.getInstance().setCurrentLogin(e.getUsername());
                        if (e.getWorkType().equals("CASHIER")) {
                            System.out.println("cash");
                            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLMainCashierWindow.fxml"));
                            Scene tableViewScene = new Scene(tableViewParent);

                            //This line gets the Stage information
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                        if (e.getWorkType().equals("ADMIN")) {
                            System.out.println("Admin");
                            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
                            Scene tableViewScene = new Scene(tableViewParent);

                            //This line gets the Stage information
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            window.setScene(tableViewScene);
                            window.show();
                        }
                    } else {
                        passwordErr.setVisible(true);
                    }
                }
            }
        }
        if(!flag){
            usernameErr.setVisible(true);
        }
    }
}