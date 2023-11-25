module dictionary.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.json;

    opens   dictionary.dictionary to javafx.fxml;
    exports dictionary.dictionary;
    exports dictionary.dictionary.DictionaryCommandLine;
    opens dictionary.dictionary.DictionaryCommandLine to javafx.fxml;
    exports dictionary.dictionary.Controller;
    opens dictionary.dictionary.Controller to javafx.fxml;
}