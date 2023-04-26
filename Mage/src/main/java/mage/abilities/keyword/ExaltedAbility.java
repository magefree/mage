package mage.abilities.keyword;

import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;

/**
 *
 * @author awjackson
 */
public class ExaltedAbility extends AttacksAloneControlledTriggeredAbility {

    public ExaltedAbility() {
        super(new BoostTargetEffect(1, 1), true, false);
    }

    private ExaltedAbility(final ExaltedAbility ability) {
        super(ability);
    }

    @Override
    public ExaltedAbility copy() {
        return new ExaltedAbility(this);
    }

    @Override
    public String getRule() {
        return "exalted <i>(Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)</i>";
    }
}
