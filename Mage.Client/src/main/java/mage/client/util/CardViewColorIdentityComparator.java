
package mage.client.util;

import java.util.Comparator;
import mage.utils.CardUtil;
import mage.view.CardView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardViewColorIdentityComparator implements Comparator<CardView> {

    @Override
    public int compare(CardView o1, CardView o2) {
        return CardUtil.getColorIdentitySortValue(o1.getManaCost(), o1.getColor(), o1.getRules())
                - CardUtil.getColorIdentitySortValue(o2.getManaCost(), o2.getColor(), o2.getRules());
    }
}
