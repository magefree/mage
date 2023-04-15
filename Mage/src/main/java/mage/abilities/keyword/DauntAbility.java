package mage.abilities.keyword;

import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 * @author TheElk801
 */
public class DauntAbility extends SimpleEvasionAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public static FilterCreaturePermanent getFilter() {
        return filter;
    }

    public DauntAbility() {
        super(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield));
    }

    private DauntAbility(final DauntAbility ability) {
        super(ability);
    }

    @Override
    public DauntAbility copy() {
        return new DauntAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} can't be blocked by creatures with power 2 or less.";
    }
}
