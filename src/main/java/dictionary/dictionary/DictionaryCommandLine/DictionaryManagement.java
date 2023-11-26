package dictionary.dictionary.DictionaryCommandLine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    private final String filePath = "src/main/resources/dictionary/wordList.txt";

    /**
     * import Dictionary form filePath.
     */
    public void insertFromFile(Dictionary dictionary,String filePath) {
        try {
            // Read the entire content of the file as a string
            String content = new String(Files.readAllBytes(Path.of(filePath)));

            // Parse the string content to create a JSONArray
            JSONArray jsonArray = new JSONArray(content);
            // Iterate through the JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Retrieve fields from the JSONObject
                String name = jsonObject.getString("name");
                String type = jsonObject.getString("type");
                String definition = jsonObject.getString("definition");

                Word newWord = new Word(name, definition, type);
                dictionary.add(newWord);
            }
            Collections.sort(dictionary);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }


    public void addWord(Dictionary dictionary, int index, Word word) {
        try {
            dictionary.add(index, word);
            exportToFile(dictionary, filePath);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    public void deleteWord(Dictionary dictionary, int index) {
        try {
            dictionary.remove(index);
            dictionary.setAllWords(dictionary);
            exportToFile(dictionary, filePath);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    public void updateWord(Dictionary dictionary, int index, String wordExplain) {
        try {
            dictionary.get(index).setWordExplain(wordExplain);
            exportToFile(dictionary, filePath);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    /**
     * export the dictionary into file.
     */
    public void exportToFile(Dictionary dictionary, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<Word> words = dictionary.getAllWords();
            for (Word word : words) {
                writer.write("{\n\t\"name\": " + word.getWordTarget() + "\",\n"
                            + "\"type\": " + "" + "\",\n"
                            + "\"definition\": " + word.getWordExplain() + "\"\n},\n");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


