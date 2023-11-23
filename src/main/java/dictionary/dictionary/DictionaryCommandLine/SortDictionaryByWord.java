package dictionary.dictionary.DictionaryCommandLine;

import java.util.Comparator;
public class SortDictionaryByWord implements Comparator<Word> {
    public int compare(Word word1, Word word2) {
        return word1.getWordTarget().compareTo(word2.getWordTarget());
    }
}
