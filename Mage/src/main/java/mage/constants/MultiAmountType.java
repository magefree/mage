package mage.constants;

import mage.util.CardUtil;
import mage.util.MultiAmountMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MultiAmountType {

    MANA("Add mana", "Distribute mana among colors"),
    DAMAGE("Assign damage", "Assign damage among targets"),
    P1P1("Add +1/+1 counters", "Distribute +1/+1 counters among creatures"),
    COUNTERS("Choose counters", "Move counters");

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

    public static List<Integer> prepareDefaltValues(List<MultiAmountMessage> constraints, int min, int max) {
        // default values must be assigned from first to last by minimum values
        List<Integer> res = constraints.stream().map(m -> m.min > Integer.MIN_VALUE ? m.min : (0 < max ? 0 : max))
                .collect(Collectors.toList());
        if (res.isEmpty()) {
            return res;
        }

        int total = res.stream().reduce(0, Integer::sum);

        // Fill values until we reach the overall minimum. Do this by filling values up until either their max or however much is leftover, starting with the first option.
        if (min > 0 && total < min) {
            int left = min - total;
            for (int i = 0; i < res.size(); i++) {
                // How much space there is left to add to 
                if (constraints.get(i).max == Integer.MAX_VALUE || constraints.get(i).max - res.get(i) > left) {
                    res.set(i, res.get(i) + left);
                    break;
                } else {
                    int add = constraints.get(i).max - res.get(i);
                    res.set(i, constraints.get(i).max);

                    left -= add;
                }
            }
        }

        return res;
    }

    public static List<Integer> prepareMaxValues(List<MultiAmountMessage> constraints, int min, int max) {
        if (constraints.isEmpty()) {
            return new ArrayList<Integer>();
        }

        // Start by filling in minimum values where it makes sense
        int default_val = max / constraints.size();
        List<Integer> res = constraints.stream()
                .map(m -> m.min > Integer.MIN_VALUE ? m.min : (default_val < m.max ? default_val : m.max))
                .collect(Collectors.toList());

        // Total should fall between the sum of all of the minimum values and max (in the case that everything was filled with default_value).
        // So, we'll never start with too much.
        int total = res.stream().reduce(0, Integer::sum);

        // So add some values evenly until we hit max
        while (total < max) {
            // Find the most amount we can add to several items at once without going over the maximum values
            int addable = Integer.MIN_VALUE;
            List<Integer> consider = new ArrayList<Integer>();
            for (int i = 0; i < res.size(); i++) {

                if (constraints.get(i).max == Integer.MAX_VALUE) {
                    consider.add(i);
                } else {
                    int diff = constraints.get(i).max - res.get(i);
                    if (diff > 0) {
                        consider.add(i);
                        if (diff < addable) {
                            addable = diff;
                        }
                    }
                }
            }

            // We hit max for all of the individual constraints - so this is as far as we can go.
            if (consider.isEmpty()) {
                break;
            }

            if (addable > Integer.MIN_VALUE && total + addable * consider.size() < max) {
                for (int i : consider) {
                    res.set(i, res.get(i) + addable);
                }
                total += addable * consider.size();
            } else {
                addable = (max - total) / consider.size();
                int extras = (max - total) % consider.size();

                for (int i = 0; i < consider.size(); i++) {
                    // Remove from the end options first
                    int idx = consider.get(i);

                    // Add the extras evenly to the first options
                    if (i < extras) {
                        res.set(idx, res.get(idx) + addable + 1);
                    } else {
                        res.set(idx, res.get(idx) + addable);
                    }
                }

                total = max;
            }
        }

        return res;
    }

    public static boolean isGoodValues(List<Integer> values, List<MultiAmountMessage> constraints, int min, int max) {
        if (values.size() != constraints.size()) {
            return false;
        }

        int currentSum = 0;
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i);

            if (value < constraints.get(i).min || value > constraints.get(i).max) {
                return false;
            }

            currentSum += value;
        }

        return currentSum >= min && currentSum <= max;
    }

    public static List<Integer> parseAnswer(String answerToParse, List<MultiAmountMessage> constraints, int min,
            int max, boolean returnDefaultOnError) {
        List<Integer> res = new ArrayList<>();

        // parse
        String normalValue = answerToParse.trim();
        if (!normalValue.isEmpty()) {
            Arrays.stream(normalValue.split(" ")).forEach(valueStr -> {
                res.add(CardUtil.parseIntWithDefault(valueStr, 0));
            });
        }

        // data check
        if (returnDefaultOnError && !isGoodValues(res, constraints, min, max)) {
            // on broken data - return default
            return prepareDefaltValues(constraints, min, max);
        }

        return res;
    }
}
