package dictionary.dictionary.Controller;

import dictionary.dictionary.Alerts.Alerts;
import dictionary.dictionary.DictionaryCommandLine.Dictionary;
import dictionary.dictionary.DictionaryCommandLine.DictionaryManagement;
import dictionary.dictionary.DictionaryCommandLine.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class additionController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final String filePath = "dictionary/wordList.txt";
    private Alerts alerts = new Alerts();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile(dictionary, filePath);
        if (wordExplanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty()) {
            addButton.setDisable(true);
        }
        backButton.setOnAction(event ->
                transferScene(event, "/dictionary/view/searchGUI.fxml"));

        wordTargetInput.setOnKeyTyped(keyEvent -> {
            if (wordExplanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty())
                addButton.setDisable(true);
            else addButton.setDisable(false);
        });

        wordExplanationInput.setOnKeyTyped(keyEvent -> {
            if (wordExplanationInput.getText().isEmpty() || wordTargetInput.getText().isEmpty())
                addButton.setDisable(true);
            else addButton.setDisable(false);
        });

        successAlert.setVisible(false);
    }

    protected boolean isExistSpelling() {
        String wordTarget = wordTargetInput.getText().trim();
        Word word = dictionary.dictionarySearchWord(wordTarget);
        return word != null && word.getWordTarget().equals(wordTarget);
    }


    @FXML
    private void handleAddButton() {
        Alert alertConfirmation = alerts.alertConfirmation("Add word", "Are you sure you want to add this word?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        String wordTarget = wordTargetInput.getText().trim();
        String wordExplain = wordExplanationInput.getText().trim();
        if (option.get() == ButtonType.OK) {
            Word word = new Word(wordTarget, wordExplain, "");
            int index = dictionary.dictionarySearchIndex(wordTarget);
            if (!isExistSpelling()) {
                dictionaryManagement.addWord(dictionary, index, word);
                System.out.println(dictionary.get(dictionary.size() - 1));
                showSuccessAlert();
            }
            addButton.setDisable(true);
            resetInput();
        } else if (option.get() == ButtonType.CANCEL)
            alerts.showAlertInfo("Information", "Changes not recognized.");
    }

    private void resetInput() {
        wordTargetInput.setText("");
        wordExplanationInput.setText("");
    }

    private void showSuccessAlert() {
        successAlert.setVisible(true);
    }

    public void transferScene(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button addButton, backButton;

    @FXML
    private TextField wordTargetInput;

    @FXML
    private TextArea wordExplanationInput;

    @FXML
    private Label successAlert;
}