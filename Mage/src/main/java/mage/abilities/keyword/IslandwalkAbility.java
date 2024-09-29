
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class IslandwalkAbility extends LandwalkAbility {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("island");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public IslandwalkAbility() {
        this(true);
    }

    public IslandwalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    protected IslandwalkAbility(final IslandwalkAbility ability) {
        super(ability);
    }

    @Override
    public IslandwalkAbility copy() {
        return new IslandwalkAbility(this);
    }
}
