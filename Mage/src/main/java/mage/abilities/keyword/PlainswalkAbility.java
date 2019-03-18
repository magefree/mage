
package mage.abilities.keyword;

import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlainswalkAbility extends LandwalkAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("plains");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
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
