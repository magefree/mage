

package mage.target.common;

import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetBasicLandCard extends TargetCard {

    public TargetBasicLandCard(Zone zone) {
        super(zone);
        filter.add(new SupertypePredicate(SuperType.BASIC));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public TargetBasicLandCard(final TargetBasicLandCard target) {
        super(target);
    }

    @Override
    public TargetBasicLandCard copy() {
        return new TargetBasicLandCard(this);
    }
}
