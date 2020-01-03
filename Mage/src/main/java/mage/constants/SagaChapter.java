package mage.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LevelX2
 */
public enum SagaChapter {
    CHAPTER_I(1, "I"),
    CHAPTER_II(2, "II"),
    CHAPTER_III(3, "III"),
    CHAPTER_IV(4, "IV");

    private static final Map<Integer, SagaChapter> chapterMap = new HashMap();

    private final String text;
    private final int number;

    SagaChapter(int number, String text) {
        this.number = number;
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    public static SagaChapter getChapter(int number) {
        initMap();
        return chapterMap.get(number);
    }

    private static void initMap() {
        if (!chapterMap.isEmpty()) {
            return;
        }
        Arrays.stream(SagaChapter.values()).forEach(sagaChapter -> chapterMap.put(sagaChapter.getNumber(), sagaChapter));
    }
}
