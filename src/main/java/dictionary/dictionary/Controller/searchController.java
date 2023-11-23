package dictionary.dictionary.Controller;

import dictionary.dictionary.DictionaryCommandLine.Dictionary;
import dictionary.dictionary.DictionaryCommandLine.DictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class searchController implements Initializable {
    private Dictionary dictionary = new Dictionary();
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final String path = "src/main/resources/dictionary/wordList.txt";
    ObservableList<String> list = FXCollections.observableArrayList();
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private TextField searchField;

    @FXML
    private Button cancelBtn, saveBtn;

    @FXML
    private Label Search, Denifition ;

    @FXML
    private TextArea explanation;

    @FXML
    private ListView<String> listResults;
    }





