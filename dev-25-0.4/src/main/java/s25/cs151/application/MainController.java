

package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainController {

    @FXML
    public void onManageOfficeHoursClick(ActionEvent event) {
        try {
            System.out.println("Clicked Manage Office Hours"); // Debug line

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/office_hours.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1920, 1080));
            stage.setTitle("Manage Office Hours");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

