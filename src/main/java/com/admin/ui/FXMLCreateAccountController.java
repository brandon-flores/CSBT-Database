/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.admin.ui;

import com.database.Employee;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FXMLCreateAccountController implements Initializable {
    @FXML
    private JFXButton createAccountCancelButton;

    @FXML
    private JFXButton createAccountCreateButton;

    @FXML
    private TextField createAccountUsername;

    @FXML
    private Label firstErr;

    @FXML
    private Label lastErr;

    @FXML
    private Label userErr;

    @FXML
    private Label typeErr;

    @FXML
    private Label passErr;

    @FXML
    private Label existsErr;

    @FXML
    private Text errorCheck;

    @FXML
    private TextField createAccountPassword;

    @FXML
    private ComboBox createAccountType;

    @FXML
    private TextField createAccountFirstName;

    @FXML
    private TextField createAccountLastName;

    private DatabaseReference database;

    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();

    private boolean success = false;
    private String accountType;
    @FXML
    void createAccountCancelPressed(ActionEvent event) {
        Stage stage = (Stage) createAccountCancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createProfileCreatePressed(ActionEvent event) {
        String username = createAccountUsername.getText();
        String password = createAccountPassword.getText();
        String firstName = createAccountFirstName.getText();
        String lastName = createAccountLastName.getText();
        accountType = ""; //I set account type outside due to local variable error -Carlo
        boolean error = false;
        existsErr.setVisible(false);
        try {
            accountType = createAccountType.getValue().toString();
            typeErr.setText("");
            typeErr.setVisible(false);
        } catch (NullPointerException e) {
            typeErr.setText("* - Type required");
            typeErr.setVisible(true);
            error = true;
        }
        boolean clearType = false;
//        System.out.println(username);

        if(firstName.equals("")) {
            firstErr.setText("* - First name required");
            firstErr.setVisible(true);
            error = true;
        } else {
            firstErr.setVisible(false);
        }

        if(lastName.equals("")) {
            lastErr.setText("* - Last name required");
            lastErr.setVisible(true);
            error = true;
        } else {
            lastErr.setVisible(false);
        }

        if(username.equals("")) {
            userErr.setText("* - Username required");
            userErr.setVisible(true);
            error = true;
        } else {
            userErr.setVisible(false);
        }

        if(password.equals("")) {
            passErr.setText("* - Password required");
            passErr.setVisible(true);
            error = true;
        } else {
            passErr.setVisible(false);
        }

        if(clearType) {
            typeErr.setVisible(false);
        }
        if(error){
         //DO NOTHING
        } else {
            DatabaseReference ref = database.child("Employees").child(username);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    lock.lock();

                    if (snapshot.getChildrenCount() > 0) {
                        //todo throw error or what
                        System.out.println("lolexist");
                        existsErr.setText("* - username already exists?");
                        existsErr.setVisible(true);
                        success = false;
                    } else {
                        Employee employee = new Employee(username, password, firstName, lastName, accountType);
                        ref.setValue(employee);
                        success = true;
                    }
                    cond.signal();
                    lock.unlock();
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
            lock.lock();
            try {
                cond.await();
                if (success) {
                    Stage stage = (Stage) createAccountCreateButton.getScene().getWindow();
                    stage.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("HERE");
        createAccountType.getItems().addAll("ADMIN", "CASHIER");
        createAccountType.setVisibleRowCount(3);
        createAccountType.setEditable(false);
        createAccountType.setPromptText("");

        database = FirebaseDatabase.getInstance().getReference();
    }    
    
}
