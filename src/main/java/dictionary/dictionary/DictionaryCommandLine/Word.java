package dictionary.dictionary.DictionaryCommandLine;

public class Word implements Comparable<Word> {
    private String word_target;
    private String word_explain;
    private String word_type;


    public Word(String word_target, String word_explain, String word_type) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public String getWordTarget() {
        return word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public  void setWordTarget (String word_target) {
        this.word_target = word_target;
    }

    public void setWordExplain (String word_explain) {
        this.word_explain = word_explain;
    }

    @Override
    public int compareTo(Word otherWord) {
        return this.word_target.compareTo(otherWord.getWordTarget());
    }

    @Override
    public String toString() {
        return "Word{" +
                "word_target='" + word_target + '\'' +
                ", word_explain='" + word_explain + '\'' +
                ", word_type='" + word_type + '\'' +
                '}';
    }
}

