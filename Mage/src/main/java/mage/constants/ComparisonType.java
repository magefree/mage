package mage.constants;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum ComparisonType {
    FEWER_THAN("<", "fewer", "than"),
    EQUAL_TO("==", "equal", "to"),
    MORE_THAN(">", "more", "than");

    String operator;
    String text1;
    String text2;

    ComparisonType(String op, String text1, String text2) {
        this.operator = op;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public String toString() {
        return operator;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public static boolean compare(int source, ComparisonType comparison, int target) {
        switch (comparison) {
            case MORE_THAN:
                return source > target;
            case FEWER_THAN:
                return source < target;
            case EQUAL_TO:
                return source == target;
            default:
                throw new IllegalArgumentException("comparison rules for " + comparison + " missing");
        }
    }
}
