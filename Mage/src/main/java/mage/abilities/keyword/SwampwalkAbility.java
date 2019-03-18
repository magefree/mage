
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SwampwalkAbility extends LandwalkAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("swamp");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public SwampwalkAbility() {
        this(true);
    }

    public SwampwalkAbility(boolean withHintText) {
        super(filter, withHintText);
    }

    public SwampwalkAbility(final SwampwalkAbility ability) {
        super(ability);
    }

    @Override
    public SwampwalkAbility copy() {
        return new SwampwalkAbility(this);
    }
}
