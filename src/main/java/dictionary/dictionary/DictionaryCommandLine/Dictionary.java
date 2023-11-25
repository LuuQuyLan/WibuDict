package dictionary.dictionary.DictionaryCommandLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class Dictionary extends ArrayList<Word> {
    static List<Word> words;

    public Dictionary() {
        words = new ArrayList<>();
    }
    public void push(Word word) {
        int index = searchIndex(0, words.size()-1, word.getWordTarget());
        if (index <= words.size() && index >= 0) words.add(index, word);
    }

    public int searchIndex(int start, int end, String wordTarget) {
        if (end < start) return start;
        int mid = start + (end - start) / 2;
        if (mid == words.size()) return mid;
        Word word = words.get(mid);
        int compare = word.getWordTarget().compareTo(wordTarget);
        if (compare == 0) return -1;
        if (compare > 0) return searchIndex(start, mid - 1, wordTarget);
        return searchIndex(mid + 1, end, wordTarget);
    }

    public int dictionarySearchIndex(String wordTarget) {
        return searchIndex(0, this.size()-1, wordTarget);
    }

    public Word binaryLookup(int start, int end, String wordTarget) {
        if (end >= start) {
            int mid = start + (end - start) / 2;
            Word word = this.get(mid);
            System.out.println(word);
            String currentSpelling = word.getWordTarget();

            int compare = currentSpelling.compareTo(wordTarget);

            if (compare == 0) {
                return word;
            } else if (compare > 0) {
                return binaryLookup(start, mid - 1, wordTarget);
            } else {
                return binaryLookup(mid + 1, end, wordTarget);
            }
        }
        return null;
    }

    public int binarySearcher(int start, int end, String wordTarget) {
        if (end < start) return -1;
        int mid = start + (end - start) / 2;
        Word word = words.get(mid);
        String currentSpelling = word.getWordTarget();
        if (currentSpelling.startsWith(wordTarget)) {
            return mid;
        }
        int compare = currentSpelling.compareTo(wordTarget);
        if (compare == 0) return mid;
        if (compare > 0) return binarySearcher(start, mid - 1, wordTarget);
        return binarySearcher(mid + 1, end, wordTarget);
    }

    public Word lookupWord(String wordTarget) {
        for (Word w : this) {
            if (wordTarget.equals(w.getWordTarget())) {
                return w;
            }
        }

        return null;
//        return binaryLookup(0, this.size() - 1, wordTarget);
    }

    public ObservableList searcherWord(String wordTarget) {
        ObservableList<Word> result = FXCollections.observableArrayList();
        int index =  binarySearcher(0, words.size() - 1, wordTarget);
        if (index >= 0) {
            result.add(words.get(index));
            int left = index - 1, right = index + 1;

            while (left >= 0) {
                Word leftWord = words.get(left--);
                if (leftWord.getWordTarget().startsWith(wordTarget))
                    result.add(leftWord);
                else
                    break;
            }

            int length = words.size();
            while (right < length) {
                Word leftWord = words.get(right++);
                if (leftWord.getWordTarget().startsWith(wordTarget))
                    result.add(leftWord);
                else
                    break;
            }
        }
        return result;
    }

    public List<Word> getAllWords() {
        return words;
    }

    public void setAllWords(ArrayList<Word> words) {
        this.words = words;
    }



}


