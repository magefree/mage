package mage.client.util.comparators;

import mage.view.CardView;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewNameComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public String getCategoryName(CardView sample) {
        return sample.getName();
    }
}