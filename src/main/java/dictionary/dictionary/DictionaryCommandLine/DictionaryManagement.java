package dictionary.dictionary.DictionaryCommandLine;
import java.io.*;
import java.util.*;
public class DictionaryManagement {

    public void insertFromFile(Dictionary dictionary,String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                //for (int i = 0; i < parts.length; i++) System.out.println(parts[i]);
                if (parts.length >= 2) {
                    String wordTarget = parts[0].trim();
                    String wordExplain = parts[1].trim();
                    Word word = new Word(wordTarget, wordExplain);
                    dictionary.insertWord(word);
                }
            }

            reader.close();
            System.out.println("Successfully inserted words from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    public void dictionaryLookup(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a word to lookup: ");
        String word = scanner.nextLine();
        Word foundWord = dictionary.lookupWord(word);
        if (foundWord != null) {
            System.out.println("Meaning: " + foundWord.getWordExplain());
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public void addWord(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English word: ");
        String wordTarget = scanner.nextLine();

        System.out.print("Enter the Vietnamese meaning: ");
        String wordExplain = scanner.nextLine();

        Word word = new Word(wordTarget, wordExplain);
        dictionary.insertWord(word);
        System.out.println("Word added to the dictionary.");
    }

    public void removeWord(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to remove: ");
        String wordTarget = scanner.nextLine();

        boolean removed = dictionary.removeWord(wordTarget);
        if (removed) {
            System.out.println("Word removed from the dictionary.");
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public void updateWord(Dictionary dictionary) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to update: ");
        String wordTarget = scanner.nextLine();

        Word word = dictionary.lookupWord(wordTarget);
        if (word != null) {
            System.out.print("Enter the new Vietnamese meaning: ");
            String wordExplain = scanner.nextLine();

            word.setWordExplain(wordExplain);
            System.out.println("Word updated in the dictionary.");
        } else {
            System.out.println("Word not found in the dictionary.");
        }
    }

    public void dictionaryExportToFile(Dictionary dictionary, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<Word> words = dictionary.getAllWords();
            for (Word word : words) {
                writer.write(word.getWordTarget() + "\t" + word.getWordExplain());
                writer.newLine();
            }
            System.out.println("Dictionary exported to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


