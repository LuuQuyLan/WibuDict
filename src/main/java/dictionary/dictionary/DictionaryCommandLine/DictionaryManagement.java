package dictionary.dictionary.DictionaryCommandLine;
import dictionary.dictionary.DictionaryCommandLine.Dictionary;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    String filePath = "dictionary/wordList.txt";

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

                // Do something with the retrieved data...
//                System.out.println("Name: " + name);
//                System.out.println("Type: " + type);
//                System.out.println("Definition: " + definition);
//                System.out.println();
                Word newWord = new Word(name, definition, type);

                dictionary.add(newWord);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
    }

    public int lookUpWord(String keyWord) {
        try {
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWordTarget().compareTo(keyWord);
                if (res == 0) return mid;
                if (res <= 0) left = mid + 1;
                else right = mid - 1;
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return -1;
    }


    public void addWord(Word word) {
        dictionary.push(word);
        exportToFile(dictionary, filePath);
    }

    public void deleteWord(Dictionary dictionary, int index, String path) {
        try {
            dictionary.remove(index);
            dictionary.setAllWords(dictionary);
            exportToFile(dictionary, path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }

    public void updateWord(Dictionary dictionary, int index, String meaning, String path) {
        try {
            dictionary.get(index).setWordExplain(meaning);
            exportToFile(dictionary, path);
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
                writer.write("name: " + word.getWordTarget() + "\ndefinition: " + word.getWordExplain());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


