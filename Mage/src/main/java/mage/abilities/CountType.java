package mage.abilities;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum  CountType {
    MORE_THAN, FEWER_THAN, EQUAL_TO;


    public static boolean compare(int source, CountType comparison, int target){
        switch (comparison){
            case MORE_THAN:
                return source > target;
            case FEWER_THAN:
                return source < target;
            case EQUAL_TO:
                return source == target;
            default:
                throw new IllegalArgumentException("comparison rules for "+comparison + " missing");
        }
    }
}
