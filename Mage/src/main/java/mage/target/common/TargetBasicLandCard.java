

package mage.target.common;

import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetBasicLandCard extends TargetCard {

    public TargetBasicLandCard(Zone zone) {
        super(zone);
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(CardType.LAND.getPredicate());
    }

    public TargetBasicLandCard(final TargetBasicLandCard target) {
        super(target);
    }

    @Override
    public TargetBasicLandCard copy() {
        return new TargetBasicLandCard(this);
    }
}
