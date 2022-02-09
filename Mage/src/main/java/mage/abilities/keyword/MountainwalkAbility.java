
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
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

    public MountainwalkAbility(final MountainwalkAbility ability) {
        super(ability);
    }

    @Override
    public MountainwalkAbility copy() {
        return new MountainwalkAbility(this);
    }
}
