package mage.client.util;

import mage.constants.Rarity;
import mage.view.CardView;

import java.util.Comparator;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewRarityComparator implements Comparator<CardView> {

    @Override
    public int compare(CardView o1, CardView o2) {
        Rarity r1 = o1.getRarity();
        Rarity r2 = o2.getRarity();

        return Integer.compare(
                r1 == null ? 0 : r1.getSorting(),
                r2 == null ? 0 : r2.getSorting()
        );
    }

}