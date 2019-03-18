
package mage.constants;

/**
 *
 * @author LevelX2
 */
public enum SagaChapter {
    CHAPTER_I(1, "I"),
    CHAPTER_II(2, "II"),
    CHAPTER_III(3, "III");

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
        switch (number) {
            case 1:
                return CHAPTER_I;
            case 2:
                return CHAPTER_II;
            case 3:
                return CHAPTER_III;
            default:
                return null;
        }
    }
}
