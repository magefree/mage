
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class MountainwalkAbility extends LandwalkAbility {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public MountainwalkAbility() {
        this(true);
    }

    public MountainwalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    protected MountainwalkAbility(final MountainwalkAbility ability) {
        super(ability);
    }

    @Override
    public MountainwalkAbility copy() {
        return new MountainwalkAbility(this);
    }
}
