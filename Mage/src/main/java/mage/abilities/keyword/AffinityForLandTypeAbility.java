package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author LevelX2
 */

public class AffinityForLandTypeAbility extends SimpleStaticAbility {

    private final FilterControlledPermanent filter;

    String text;
    SubType landType;

    public AffinityForLandTypeAbility(SubType landType, String text) {
        super(Zone.ALL, new AffinityEffect(getFilter(landType)));
        this.filter = getFilter(landType);
        setRuleAtTheTop(true);
        this.text = text;
        this.landType = landType;

        this.addHint(new ValueHint(landType + " you control", new PermanentsOnBattlefieldCount(filter)));
    }

    private static FilterControlledPermanent getFilter(SubType landType) {
        FilterControlledPermanent affinityfilter = new FilterControlledPermanent();
        affinityfilter.add(new SubtypePredicate(landType));
        return affinityfilter;

    }

    public AffinityForLandTypeAbility(final AffinityForLandTypeAbility ability) {
        super(ability);
        this.text = ability.text;
        this.filter = ability.filter.copy();
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForLandTypeAbility(this);
    }

    @Override
    public String getRule() {
        return "Affinity for " + text + " <i>(This spell costs 1 less to cast for each " + landType + " you control.)</i>";
    }
}
