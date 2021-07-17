package com.admin.ui;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLAdminVoidRequestsWindowController implements Initializable {
    //BRANDON!!!!!
    Stage createAccountStage = new Stage();
    //THIS
    Stage currentStage = new Stage();

    @FXML
    private JFXButton adminVoidRequestsButton;

    @FXML
    private JFXButton adminVoidRequestsLogoutButton;

    @FXML
    private JFXButton busProfilesCreateProfileButton;

    @FXML
    private ComboBox adminVoidRequestsMenu;

    @FXML
    void adminVoidRequestsButtonPressed(ActionEvent event) throws IOException {
        //BRANDON!!!!!
        FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLCreateAccount.fxml"));
        Parent anotherRoot = anotherLoader.load();
        Scene anotherScene = new Scene(anotherRoot);
        createAccountStage.setScene(anotherScene);
        createAccountStage.initStyle(StageStyle.UNDECORATED); //removes the title bar of the window

        /**
         *  The bus profiles window is "refreshed" every time the create profile
         *  button is pressed due to an error. The error is caused from removing
         *  the title bar of the window.
         */

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

        createAccountStage.show();
    }

    @FXML
    /**
     * Entire block changes scene
     */
    void adminVoidRequestsLogoutButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../resources/FXMLLoginFormWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    void adminVoidRequestButtonPressed(ActionEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {
        /**
         * This part is for the initialization of the Combo Box.
         * TODO: Every item in the menu when chosen, another scene will be
         * opened to the item's corresponding scene (change scene/stage).
         */

        adminVoidRequestsMenu.getItems().addAll("CURRENT", "RECORDS", "VOID REQUESTS", "BUS PROFILES");
        adminVoidRequestsMenu.setVisibleRowCount(4);
        adminVoidRequestsMenu.setEditable(false);
        adminVoidRequestsMenu.setPromptText("VOID REQUESTS");

        //THIS
        adminVoidRequestsMenu.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                Stage stage = (Stage) adminVoidRequestsButton.getScene().getWindow();
                if(adminVoidRequestsMenu.getItems().get((Integer) number2).equals("CURRENT")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/com/loginform/FXMLCurrentWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (adminVoidRequestsMenu.getItems().get((Integer) number2).equals("RECORDS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLRecordsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (adminVoidRequestsMenu.getItems().get((Integer) number2).equals("VOID REQUESTS")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/FXMLAdminVoidRequestsWindow.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                } else if (adminVoidRequestsMenu.getItems().get((Integer) number2).equals("BUS PROFILES")) {
                    FXMLLoader anotherLoader = new FXMLLoader(getClass().getResource("/com/loginform/FXMLBusProfiles.fxml"));
                    Parent anotherRoot = null;
                    try {
                        anotherRoot = anotherLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene anotherScene = new Scene(anotherRoot);
                    currentStage.setScene(anotherScene);
                }
                stage.close();
                currentStage.show();
            }
        });
    }

}

