package dictionary.dictionary.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class appController implements Initializable {

    @FXML
    private Button dictionaryButton, gameButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryButton.setOnAction(event -> transferScene(event, "/dictionary/view/searchGUI.fxml"));
        gameButton.setOnAction(event -> transferScene(event, "/dictionary/view/gameGUI.fxml"));
    }

    public void transferScene(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}