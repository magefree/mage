package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public class RampageAbility extends BecomesBlockedSourceTriggeredAbility {

    private final String rule;

    public RampageAbility(int amount) {
        this(amount, false);
    }

    public RampageAbility(int amount, boolean shortRuleText) {
        super(null, false);
        rule = "rampage " + amount
                + (shortRuleText ? ""
                        : " <i>(Whenever this creature becomes blocked, it gets +"
                        + amount + "/+" + amount + " until end of turn for each creature blocking it beyond the first.)</i>");
        DynamicValue rv = (amount == 1 ? BlockingCreatureCount.BEYOND_FIRST : new MultipliedValue(BlockingCreatureCount.BEYOND_FIRST, amount));
        this.addEffect(new BoostSourceEffect(rv, rv, Duration.EndOfTurn, true));
    }

    public RampageAbility(final RampageAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public RampageAbility copy() {
        return new RampageAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }
}
