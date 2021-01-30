package mage.client.util.comparators;

import mage.view.CardView;

/**
 * @author JayDi85
 */
public class CardViewNoneComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return 0;
    }

    @Override
    public String getCategoryName(CardView sample) {
        return "";
    }
}
