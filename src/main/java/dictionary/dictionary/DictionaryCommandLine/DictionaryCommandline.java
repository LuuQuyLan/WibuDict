package dictionary.dictionary.DictionaryCommandLine;
import java.util.*;
public class DictionaryCommandline extends DictionaryManagement {
    private static final String FILE_PATH = "src/dictionaries.txt";
    private final Dictionary dictionary;
    public DictionaryCommandline() {
        dictionary = new Dictionary();
    }

    public void showMenu() {
        System.out.println("Welcome to My Application!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export to file");
        System.out.print("Your action: ");
    }

    public void runApplication() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            showMenu();

            String input = scanner.nextLine();
            try {
                int action = Integer.parseInt(input);
                switch (action) {
                    case 0:
                        exit = true;
                        break;
                    case 1:
                        addWord();
                        break;
                    case 2:
                        removeWord(dictionary);
                        break;
                    case 3:
                        updateWord(dictionary);
                        break;
                    case 4:
                        showAllWords();
                        break;
                    case 5:
                        dictionaryLookup(dictionary);
                        break;
                    case 6:
                        dictionarySearcher();
                        break;
                    case 8:
                        insertFromFile(dictionary,FILE_PATH);
                        break;
                    case 9:
                        dictionaryExportToFile(dictionary, FILE_PATH);
                        break;
                    default:
                        System.out.println("Action not supported.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Action not supported.");
            }
        }
    }

    public void showAllWords() {
        List<Word> words = dictionary.getAllWords();
        System.out.println(words.size());
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            System.out.printf("%d. %s: %s%n", (i + 1), word.getWordTarget(), word.getWordExplain());
        }
    }

    public void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a prefix to search: ");
        String prefix = scanner.nextLine();

        List<String> searchResults = dictionary.searchWords(prefix);
        if (!searchResults.isEmpty()) {
            System.out.println("Words starting with \"" + prefix + "\":");
            for (String word : searchResults) {
                System.out.println(word);
            }
        } else {
            System.out.println("No words found.");
        }
    }

    public void addWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the English word: ");
        String wordTarget = scanner.nextLine();

        System.out.print("Enter the Vietnamese meaning: ");
        String wordExplain = scanner.nextLine();

        Word word = new Word(wordTarget, wordExplain);
        dictionary.insertWord(word);
        System.out.println("Word added to the dictionary.");
    }

}


