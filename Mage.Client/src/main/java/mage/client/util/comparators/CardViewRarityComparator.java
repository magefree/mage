package mage.client.util.comparators;

import mage.constants.Rarity;
import mage.view.CardView;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewRarityComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        Rarity r1 = o1.getRarity();
        Rarity r2 = o2.getRarity();

        return Integer.compare(
                r1 == null ? 0 : r1.getSorting(),
                r2 == null ? 0 : r2.getSorting()
        );
    }

    @Override
    public String getCategoryName(CardView sample) {
        return sample.getRarity().toString();
    }
}