package dictionary.dictionary.Controller;

import dictionary.dictionary.Alerts.Alerts;
import dictionary.dictionary.DictionaryCommandLine.Dictionary;
import dictionary.dictionary.DictionaryCommandLine.DictionaryManagement;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class searchController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    private final String filePath = "dictionary/wordList.json";
    ObservableList<String> list = FXCollections.observableArrayList();
    private Alerts alerts = new Alerts();
    private int indexOfWord;
    private int firstIndexOfListFound = 0;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("abc");
        dictionaryManagement.insertFromFile(dictionary, filePath);
        setListResult();

        cancelButton.setVisible(false);
        selectedWordTarget.setVisible(false);
        explanation.setVisible(false);
        speakerButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveButton.setVisible(false);

        searchField.setOnKeyTyped(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!searchField.getText().isEmpty()) {
                    // Call the search function
                    cancelButton.setVisible(true);
                    handleOnKeyTyped();
                } else {
                    cancelButton.setVisible(false);
                }
            }
        });

        cancelButton.setOnAction(event -> {
            searchField.clear();
            cancelButton.setVisible(false);
            firstIndexOfListFound = 0;
            setListResult();
        });

        backButton.setOnAction(event ->
                transferScene(event, "/dictionary/view/appGUI.fxml"));
        additionButton.setOnAction(event ->
                transferScene(event, "/dictionary/view/additionGUI.fxml"));

    }


    @FXML
    private void handleOnKeyTyped() {
        list.clear();
        String searchText = searchField.getText();
        list = dictionary.searcherWord(searchText);
        if (list.isEmpty()) {
            firstIndexOfListFound = 0;
            setListResult();
        } else {
            firstIndexOfListFound = dictionary.dictionarySearchIndex(list.get(0).toString());
            setListResult();
        }
    }

    @FXML
    private void handleMouseClickAWord(MouseEvent arg0) {
        String selectedWord = listResult.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            indexOfWord = dictionary.dictionarySearchIndex(selectedWord);
            if (indexOfWord == -1) return;
            String wordTarget = dictionary.get(indexOfWord).getWordTarget();
            String wordExplain = dictionary.get(indexOfWord).getWordExplain();

            selectedWordTarget.setText(wordTarget);
            explanation.setText(wordExplain);

            selectedWordTarget.setVisible(true);
            explanation.setVisible(true);
            speakerButton.setDisable(false);
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    @FXML
    private void handleClickEditButton() {
        explanation.setEditable(true);
        saveButton.setVisible(true);
        alerts.showAlertInfo("Information", "Now you can edit this word's explanation!");
    }

    //@FXML
    //private void handleClickSoundButton() {
    //    System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    //    Voice voice = VoiceManager.getInstance().getVoice("kevin16");
    //    if (voice != null) {
    //        voice.allocate();
    //        voice.speak(dictionary.get(indexOfWord).getWordTarget());
    //    } else throw new IllegalStateException("Cannot find voice: kevin16");
    //}

    @FXML
    private void handleClickSaveButton() {
        Alert alertConfirmation = alerts.alertConfirmation("Update", "Are you sure you want to update this word?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            indexOfWord = dictionary.dictionarySearchIndex(selectedWordTarget.getText());
            dictionaryManagement.updateWord(dictionary, indexOfWord, explanation.getText());
            alerts.showAlertInfo("Information", "Successfully updated!");
        } else alerts.showAlertInfo("Information", "Changes not recognized!");
        saveButton.setVisible(false);
        explanation.setEditable(false);
    }

    @FXML
    private void handleClickDeleteButton() {
        Alert alertWarning = alerts.alertWarning("Delete", "Are you sure you want to delete this word?");
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> option = alertWarning.showAndWait();
        if (option.get() == ButtonType.OK) {
            indexOfWord = dictionary.dictionarySearchIndex(selectedWordTarget.getText());
            dictionaryManagement.deleteWord(dictionary, indexOfWord);
            refreshAfterDeleting();
            alerts.showAlertInfo("Information", "Successfully deleted");
        } else alerts.showAlertInfo("Information", "Changes not recognized!");
    }

    private void setListResult() {
        for (int i = firstIndexOfListFound; i < firstIndexOfListFound + 15 && i + 15 < dictionary.size(); i++) {
            list.add(dictionary.get(i).getWordTarget());
        }
        listResult.setItems(list);
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
    private void refreshAfterDeleting() {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(selectedWordTarget.getText())) {
                list.remove(i);
                break;
            }
        listResult.setItems(list);
        selectedWordTarget.setVisible(false);
        explanation.setVisible(false);
    }



    @FXML
    private TextField searchField;

    @FXML
    private Button cancelButton, backButton, additionButton, speakerButton, editButton, deleteButton, saveButton;

    @FXML
    private Label selectedWordTarget;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResult;

}





