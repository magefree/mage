
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MountainwalkAbility extends LandwalkAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("mountain");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
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
