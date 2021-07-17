package excluded;

import com.database.Bus;
//import com.database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class FXMLLoginFormWindowController implements Initializable {

    public void accountantButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLAccountantWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void cashierButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLArrivalWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void adminButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FXMLCurrentWindow.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Database database = Database.database;
//        LocalDate date1 = LocalDate.of(2017, Month.OCTOBER, 12);
//        LocalDate date2 = LocalDate.of(2017, Month.OCTOBER, 13);
//        LocalDate date3 = LocalDate.of(2017, Month.OCTOBER, 14);
//        LocalDate date4 = LocalDate.of(2017, Month.OCTOBER, 15);
//        database.addBus(new Bus("ABC123", "SUNRAYS", "1"));
//        database.addBus(new Bus("ACB223", "CERES LINER", "1"));
//        database.addBus(new Bus("ACC213", "JEGANS"));
//        database.addBus(new Bus("ACD213", "CALVO"));
//        database.addBus(new Bus("ACE213", "COROMINAS"));
//        database.addBus(new Bus("ACF213", "GABE TRANSIT"));
//        database.addBus(new Bus("ACG213", "CANONEO"));

    }
}
