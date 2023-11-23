package dictionary.dictionary.DictionaryCommandLine;
import java.util.ArrayList;
import java.util.List;


public class Dictionary {
    private final List<Word> words;

    public Dictionary() {
        words = new ArrayList<>();
    }
    public void insertWord(Word word) {
        words.add(word);
    }

    public boolean removeWord(String wordTarget) {
        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                words.remove(word);
                return true;
            }
        }
        return false;
    }

    public Word lookupWord(String wordTarget) {
        for (Word word : words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                return word;
            }
        }
        return null;
    }

    public List<Word> getAllWords() {
        return words;
    }

    public List<String> searchWords(String prefix) {
        List<String> searchResults = new ArrayList<>();
        for (Word word : words) {
            if (word.getWordTarget().startsWith(prefix)) {
                searchResults.add(word.getWordTarget());
            }
        }
        return searchResults;
    }

}


