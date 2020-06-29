package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.combat.CombatGroup;

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
        RampageValue rv = new RampageValue(amount);
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

class RampageValue implements DynamicValue {

    private final int amount;

    public RampageValue(int amount) {
        this.amount = amount;
    }

    public RampageValue(final RampageValue value) {
        this.amount = value.amount;
    }

    @Override
    public RampageValue copy() {
        return new RampageValue(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(sourceAbility.getSourceId())) {
                int blockers = combatGroup.getBlockers().size();
                return blockers > 1 ? (blockers - 1) * amount : 0;
            }
        }
        return 0;
    }

    @Override
    public String getMessage() {
        return "rampage " + amount + "(Whenever this creature becomes blocked, it gets +"
                + amount + "/+" + amount + " until end of turn for each creature blocking it beyond the first.)";
    }
}
