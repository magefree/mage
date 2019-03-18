
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class IslandwalkAbility extends LandwalkAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("island");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public IslandwalkAbility() {
        this(true);
    }

    public IslandwalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    public IslandwalkAbility(final IslandwalkAbility ability) {
        super(ability);
    }

    @Override
    public IslandwalkAbility copy() {
        return new IslandwalkAbility(this);
    }
}
