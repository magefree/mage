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

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");
    private final boolean asKeyword;

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
    }

    public static FilterCreaturePermanent getFilter() {
        return filter;
    }

    public DauntAbility() {
        this(false);
    }

    public DauntAbility(boolean asKeyword) {
        super(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield));
        this.asKeyword = asKeyword;
    }

    private DauntAbility(final DauntAbility ability) {
        super(ability);
        this.asKeyword = ability.asKeyword;
    }

    @Override
    public DauntAbility copy() {
        return new DauntAbility(this);
    }

    @Override
    public String getRule() {
        return this.asKeyword ? "Daunt <i>({this} can't be blocked by creatures with power 2 or less.)</i>" :
        "{this} can't be blocked by creatures with power 2 or less.";
    }
}
