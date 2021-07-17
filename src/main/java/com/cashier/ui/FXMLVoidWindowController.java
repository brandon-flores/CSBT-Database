package com.cashier.ui;

import com.database.Fee;
import com.database.FirebaseDB;
import com.database.SingletonLogin;
import com.google.firebase.database.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class FXMLVoidWindowController implements Initializable {
    Stage anotherStage = new Stage();
    String orNo = "";
    ArrayList<Fee> fees;
    Fee feeToSolve;

    @FXML
    private JFXButton voidWindowCancel;

    @FXML
    private JFXButton voidWindowSendRequest;

    @FXML
    private TextField voidWindowOrNo;

    @FXML
    private Label noVoid;

    private Lock lock = new ReentrantLock();
    private Condition cond = lock.newCondition();


    DatabaseReference database;

    @FXML
    void voidWindowCancelPressed(ActionEvent event) {
        Stage stage = (Stage) voidWindowCancel.getScene().getWindow();
        CashierMain.cancelPressed = true;
        stage.close();
    }

    @FXML
    void voidWindowSendRequestPressed(ActionEvent event) throws IOException {
        orNo = voidWindowOrNo.getText();
        orNo = orNo.replaceAll("\\s", "");//removes spaces
        //todo checking if such or exists

        if (orNo == null || orNo.equals("")) {
            System.out.println("invalid");
            noVoid.setText("* - invalid input");
        } else if (!NetChecker.netIsAvailable()) {
            noVoid.setText("No connection! Check connection");
        } else {
            DatabaseReference ref = database.child("Fees");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    lock.lock();
                    try {
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Fee f = snap.getValue(Fee.class);
                            fees.add(f);
                        }
                        System.out.println("Loop done");
                        cond.signal();
                    }
                    finally {
                        lock.unlock();
                    }
                    System.out.println("LOL");

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            lock.lock();
            try {
                System.out.println("locked");
                cond.await();
                System.out.println("Resumed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println("Done");
            if(hasOr()) {
                if(feeToSolve.getEmployeeID().equals(SingletonLogin.getInstance().getCurrentLogin())){
                    noVoid.setText("");
                    SingletonVoid.getInstance().setOrNo(orNo);
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLVoidRequestWindow.fxml"));
                    Parent anotherRoot = anotherLoader.load();
                    Scene anotherScene = new Scene(anotherRoot);
                    anotherStage.setScene(anotherScene);
                    anotherStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

                    //close void window then open void request window
                    Stage stage = (Stage) voidWindowCancel.getScene().getWindow();
                    CashierMain.cancelPressed = true;
                    stage.close();

                    voidWindowVoidPressed(event);
                }else {
                    noVoid.setText("* - Cannot void other cashier's transaction");
                }

            } else {
                noVoid.setText("* - No such OR number");
            }
        }
    }

    private boolean hasOr() {
        for (Fee f : fees) {
            System.out.println(orNo + " VS " + f.getOrNum());
            System.out.println("LOLITA");
            if(orNo.equals(f.getOrNum())){
                feeToSolve = f;
                return true;
            }

        }
        return false;
    }
    @FXML
    void voidWindowVoidPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLVoidRequestWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fees = new ArrayList<Fee>();
        noVoid.setText("");
        database = FirebaseDatabase.getInstance().getReference();
        initializeFees();
    }

    private void initializeFees() {
    }
}
