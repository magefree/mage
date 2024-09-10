package mage.constants;

/**
 * @author LevelX2
 */
public enum SagaChapter {
    CHAPTER_I(1, "I"),
    CHAPTER_II(2, "II"),
    CHAPTER_III(3, "III"),
    CHAPTER_IV(4, "IV"),
    CHAPTER_V(5, "V"),
    CHAPTER_VI(6, "VI");

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
        for (SagaChapter sagaChapter : SagaChapter.values()) {
            if (sagaChapter.getNumber() == number) {
                return sagaChapter;
            }
        }
        return null;
    }
}
