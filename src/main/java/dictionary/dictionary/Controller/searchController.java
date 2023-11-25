package dictionary.dictionary.Controller;

import dictionary.dictionary.Alerts.Alerts;
import dictionary.dictionary.DictionaryCommandLine.Dictionary;
import dictionary.dictionary.DictionaryCommandLine.DictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
public class searchController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final String path = "src/main/resources/dictionary/wordList.txt";
    ObservableList list = FXCollections.observableArrayList();
    private Alerts alerts = new Alerts();
    private int indexOfWord;
    private int firstIndexOfListFound = 0;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile(dictionary, path);
        setListDefault(0);
        searchField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (searchField.getText().isEmpty()) {
                    cancelButton.setVisible(false);
                    setListDefault(0);
                } else {
                    cancelButton.setVisible(true);
                    handleOnKeyTyped();
                }
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String searchText = searchField.getText();
                    if (!searchText.isEmpty()) {
                        // Call your search function here, passing searchText
                        dictionary.searcherWord(searchText);
                    } else {
                        cancelButton.setVisible(false);
                        setListDefault(0);
                    }
                }
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchField.clear();
                cancelButton.setVisible(false);
                setListDefault(0);
            }
        });
        backButton.setOnAction(event ->
                transferScene(event, "/dictionary/view/appGUI.fxml"));
        additionButton.setOnAction(event ->
                transferScene(event, "/dictionary/view/additionGUI.fxml"));

        explanation.setEditable(false);
        cancelButton.setVisible(false);
    }


    @FXML
    private void handleOnKeyTyped() {
        list.clear();
        String searchKey = searchField.getText().trim();
        list = dictionary.searcherWord(searchKey);
        if (list.isEmpty()) {
            setListDefault(firstIndexOfListFound);
        } else {
            headerListResult.setText("Kết quả");
            listResult.setItems(list);
            firstIndexOfListFound = dictionary.dictionarySearchIndex(list.get(0).toString());
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

            // Display the word's target in the headerExplanation
            headerExplanation.getChildren().clear();
            Label headerLabel = new Label(wordTarget);
            headerExplanation.getChildren().add(headerLabel);

            // Display the word's explanation in the explanation TextArea
            explanation.setText(wordExplain);
            headerExplanation.setVisible(true);
            explanation.setVisible(true);
            explanation.setEditable(false);
            saveButton.setVisible(false);
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
    //        voice.speak(dictionary.get(indexOfSelectedWord).getWordTarget());
    //    } else throw new IllegalStateException("Cannot find voice: kevin16");
    //}

    @FXML
    private void handleClickSaveButton() {
        Alert alertConfirmation = alerts.alertConfirmation("Update", "Are you sure you want to update this word?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            dictionaryManagement.updateWord(dictionary, indexOfWord, explanation.getText(), path);
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
            dictionaryManagement.deleteWord(dictionary, indexOfWord, path);
            refreshAfterDeleting();
            alerts.showAlertInfo("Information", "Successfully deleted");
        } else alerts.showAlertInfo("Information", "Changes not recognized!");
    }
    private void setListDefault(int index) {
        list.clear();
        for (int i = index; i < index + 15; i++) {
            list.add(dictionary.get(i).getWordTarget());
        }
        listResult.setItems(list);
        wordTarget.setText(dictionary.get(index).getWordTarget());
        explanation.setText(dictionary.get(index).getWordExplain());
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
            if (list.get(i).equals(wordTarget.getText())) {
                list.remove(i);
                break;
            }
        listResult.setItems(list);
        headerExplanation.setVisible(false);
        explanation.setVisible(false);
    }



    @FXML
    private TextField searchField;

    @FXML
    private Button cancelButton, backButton, additionButton, speakerButton, editButton, deleteButton, saveButton;

    @FXML
    private Label wordTarget, headerListResult ;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResult;

    @FXML
    private Pane headerExplanation;

}





