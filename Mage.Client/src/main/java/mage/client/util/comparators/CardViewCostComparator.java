package mage.client.util.comparators;

import mage.view.CardView;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewCostComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return Integer.compare(o1.getManaValue(), o2.getManaValue());
    }

    @Override
    public String getCategoryName(CardView sample) {
        return "CMC: " + sample.getManaValue();
    }
}
