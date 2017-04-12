package mage.constants;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum ComparisonType {
    MORE_THAN(">"), FEWER_THAN("<"), EQUAL_TO("==");

    String operator;

    ComparisonType(String op) {
        operator = op;
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
            default:
                throw new IllegalArgumentException("comparison rules for " + comparison + " missing");
        }
    }
}
