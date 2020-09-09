
package mage.client.util;

import java.util.Comparator;
import mage.view.CardView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewCostComparator implements Comparator<CardView> {

    @Override
    public int compare(CardView o1, CardView o2) {
        return Integer.compare(o1.getConvertedManaCost(), o2.getConvertedManaCost());
    }

}
