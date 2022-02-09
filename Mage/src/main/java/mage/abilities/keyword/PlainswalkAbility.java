
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlainswalkAbility extends LandwalkAbility {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("plains");

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public PlainswalkAbility() {
        this(true);
    }

    public PlainswalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    public PlainswalkAbility(final PlainswalkAbility ability) {
        super(ability);
    }

    @Override
    public PlainswalkAbility copy() {
        return new PlainswalkAbility(this);
    }
}
