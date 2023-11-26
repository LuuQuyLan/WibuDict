package dictionary.dictionary.DictionaryCommandLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;


public class Dictionary extends ArrayList<Word> {
    public static List<Word> words;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public int dictionarySearchIndex(String wordTarget) {
        return binarySearchIndex(0, this.size()-1, wordTarget);
    }
    public Word dictionarySearchWord(String wordTarget) {
        return binarySearchWord(0, this.size()-1, wordTarget);
    }

    /**
     * returns the exact index of the wordTarget in the dictionary applying binary searcher.
     */
    public int binarySearchIndex(int start, int end, String wordTarget) {
        if (end < start) return start;
        int mid = start + (end - start) / 2;
        Word word = this.get(mid);
        String currentWordTarget = word.getWordTarget();
        if (currentWordTarget.startsWith(wordTarget)) {
            return mid;
        }
        int compare = currentWordTarget.compareTo(wordTarget);
        if (compare == 0) return mid;
        if (compare > 0) return binarySearchIndex(start, mid - 1, wordTarget);
        return binarySearchIndex(mid + 1, end, wordTarget);
    }

    /**
     * returns the exact word of the wordTarget in the dictionary applying binary searcher.
     */
    public Word binarySearchWord(int start, int end, String wordTarget) {
        if (end >= start) {
            int mid = start + (end - start) / 2;
            Word word = this.get(mid);
            String currentWordTarget = word.getWordTarget();

            int compare = currentWordTarget.compareTo(wordTarget);

            if (compare == 0) {
                return word;
            } else if (compare > 0) {
                return binarySearchWord(start, mid - 1, wordTarget);
            } else {
                return binarySearchWord(mid + 1, end, wordTarget);
            }
        }
        return null;
    }

    /**
     * returns list of 15 words starting with wordTarget.
     */
    public ObservableList<String> searcherWord(String wordTarget) {
        ObservableList<String> result = FXCollections.observableArrayList();

        // Binary search for the starting index of wordTarget
        int index = binarySearchIndex(0, this.size() - 1, wordTarget);

        if (index >= 0) {
            // Add the word at the found index
            result.add(this.get(index).getWordTarget());

            // Explore left and right for words starting with wordTarget
            int left = index - 1, right = index + 1;

            while (left >= 0 && result.size() < 15) {
                String leftWord = this.get(left--).getWordTarget();
                if (leftWord.startsWith(wordTarget)) {
                    result.add(leftWord);
                } else {
                    break;
                }
            }
            while (right < this.size() && result.size() < 15) {
                String rightWord = this.get(right++).getWordTarget();
                if (rightWord.startsWith(wordTarget)) {
                    result.add(rightWord);
                } else {
                    break;
                }
            }

            // Sort the result list alphabetically
            result.sort(String::compareTo);
        }

        return result;
    }

    public List<Word> getAllWords() {
        return words;
    }

    public void setAllWords(ArrayList<Word> words) {
        Dictionary.words = words;
    }



}


