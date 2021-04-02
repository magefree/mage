package mage.constants;

import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public enum MultiAmountType {

    MANA("Add mana", "Distribute mana among colors"),
    DAMAGE("Assign damage", "Assign damage among targets");

    private final String title;
    private final String header;

    MultiAmountType(String title, String header) {
        this.title = title;
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public String getHeader() {
        return header;
    }

    public static List<Integer> prepareDefaltValues(int count, int min, int max) {
        // default values must be assigned from first to last by minimum values
        List<Integer> res = new ArrayList<>();

        // fill list
        IntStream.range(0, count).forEach(i -> res.add(0));

        // fill values
        if (min > 0 && res.size() > 0) {
            res.set(0, min);
        }

        return res;
    }

    public static boolean isGoodValues(List<Integer> values, int count, int min, int max) {
        if (values.size() != count) {
            return false;
        }

        int currentSum = values.stream().mapToInt(i -> i).sum();
        return currentSum >= min && currentSum <= max;
    }

    public static List<Integer> parseAnswer(String answerToParse, int count, int min, int max, boolean returnDefaultOnError) {
        List<Integer> res = new ArrayList<>();

        // parse
        String normalValue = answerToParse.trim();
        if (!normalValue.isEmpty()) {
            Arrays.stream(normalValue.split(" ")).forEach(valueStr -> {
                res.add(CardUtil.parseIntWithDefault(valueStr, 0));
            });
        }

        // data check
        if (returnDefaultOnError && !isGoodValues(res, count, min, max)) {
            // on broken data - return default
            return prepareDefaltValues(count, min, max);
        }

        return res;
    }
}
