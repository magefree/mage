package mage.constants;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum ComparisonType {
    FEWER_THAN("<"),
    OR_LESS("<="),
    EQUAL_TO("=="),
    MORE_THAN(">"),
    OR_GREATER(">=");

    final String operator;

    ComparisonType(String op) {
        this.operator = op;
    }

    @Override
    public String toString() {
        return operator;
    }

    public static boolean compare(int source, ComparisonType comparison, int target) {
        switch (comparison) {
            case MORE_THAN:
                return source > target;
            case FEWER_THAN:
                return source < target;
            case EQUAL_TO:
                return source == target;
            case OR_GREATER:
                return source >= target;
            case OR_LESS:
                return source <= target;
            default:
                throw new IllegalArgumentException("comparison rules for " + comparison + " missing");
        }
    }
}
