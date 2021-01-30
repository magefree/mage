package mage.client.util.comparators;

import mage.view.CardView;

import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CardViewColorComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return o1.getColor().compareTo(o2.getColor());
    }

    @Override
    public String getCategoryName(CardView sample) {
        String res = sample.getColor().getColors()
                .stream()
                .map(c -> "{" + c.getOneColoredManaSymbol() + "}")
                .collect(Collectors.joining(""));
        if (res.isEmpty()) {
            res = "{C}"; // colorless
        }
        return res;
    }
}