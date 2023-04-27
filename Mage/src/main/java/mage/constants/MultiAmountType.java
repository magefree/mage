package mage.constants;

import com.google.common.collect.Iterables;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public enum MultiAmountType {

    MANA("Add mana", "Distribute mana among colors"),
    DAMAGE("Assign damage", "Assign damage among targets"),
    P1P1("Add +1/+1 counters", "Distribute +1/+1 counters among creatures");

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
        if (count == 0) {
            return res;
        }

        // fill list
        IntStream.range(0, count).forEach(i -> res.add(0));

        // fill values
        if (min > 0) {
            res.set(0, min);
        }

        return res;
    }

    public static List<Integer> prepareMaxValues(int count, int min, int max) {
        // fill max values as much as possible
        List<Integer> res = new ArrayList<>();
        if (count == 0) {
            return res;
        }

        // fill list
        int startingValue = max / count;
        IntStream.range(0, count).forEach(i -> res.add(startingValue));

        // fill values
        // from first to last until complete
        List<Integer> resIndexes = new ArrayList<>(res.size());
        IntStream.range(0, res.size()).forEach(resIndexes::add);
        // infinite iterator (no needs with starting values use, but can be used later for different logic)
        Iterator<Integer> resIterator = Iterables.cycle(resIndexes).iterator();
        int valueInc = 1;
        int valueTotal = startingValue * count;
        while (valueTotal < max) {
            int currentIndex = resIterator.next();
            int newValue = CardUtil.overflowInc(res.get(currentIndex), valueInc);
            res.set(currentIndex, newValue);
            valueTotal += valueInc;
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
