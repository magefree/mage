
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SwampwalkAbility extends LandwalkAbility {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public SwampwalkAbility() {
        this(true);
    }

    public SwampwalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    protected SwampwalkAbility(final SwampwalkAbility ability) {
        super(ability);
    }

    @Override
    public SwampwalkAbility copy() {
        return new SwampwalkAbility(this);
    }
}
