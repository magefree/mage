package mage.util;

/**
 * Created by IGOUDT on 16-9-2016.
 */
public class StringUtil {

    public static boolean isEmpty(String input){
        return (input == null) || input.isEmpty();
    }

    public static boolean isNotEmpty(String input){
        return !isEmpty(input);
    }
}
