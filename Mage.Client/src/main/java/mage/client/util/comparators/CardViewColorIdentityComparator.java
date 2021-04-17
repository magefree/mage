package mage.client.util.comparators;

import mage.filter.FilterMana;
import mage.util.ManaUtil;
import mage.view.CardView;

/**
 * @author BetaSteward_at_googlemail.com, JayDi85
 */
public class CardViewColorIdentityComparator implements CardViewComparator {

    @Override
    public int compare(CardView o1, CardView o2) {
        return calcHash(o1) - calcHash(o2);
    }

    @Override
    public String getCategoryName(CardView sample) {
        String res = calcIdentity(sample).toString();
        if (res.isEmpty()) {
            res = "{C}"; // colorless
        }
        return res;
    }

    public static FilterMana calcIdentity(CardView cardView) {
        return ManaUtil.getColorIdentity(cardView.getColor(), cardView.getManaCostStr(), cardView.getRules(), null);
    }

    public static int calcHash(CardView cardView) {
        return ManaUtil.getColorIdentityHash(calcIdentity(cardView));
    }
}
