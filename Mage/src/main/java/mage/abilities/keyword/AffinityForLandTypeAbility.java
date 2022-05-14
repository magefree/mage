package mage.abilities.keyword;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author LevelX2
 */

public class AffinityForLandTypeAbility extends SimpleStaticAbility {
    private final String rulesText;

    public AffinityForLandTypeAbility(SubType landType, String pluralName) {
        super(Zone.ALL, null);
        rulesText = "Affinity for " + pluralName + " <i>(This spell costs {1} less to cast for each " + landType + " you control.)</i>";
        setRuleAtTheTop(true);

        FilterControlledPermanent filter = new FilterControlledPermanent(landType);
        addEffect(new AffinityEffect(filter));
        addHint(new ValueHint(pluralName + " you control", new PermanentsOnBattlefieldCount(filter)));
    }

    public AffinityForLandTypeAbility(final AffinityForLandTypeAbility ability) {
        super(ability);
        this.rulesText = ability.rulesText;
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AffinityForLandTypeAbility(this);
    }

    @Override
    public String getRule() {
        return rulesText;
    }
}
