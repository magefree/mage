package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.counters.CounterType;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class RavenousAbility extends EntersBattlefieldAbility {

    public RavenousAbility() {
        super(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance()));
        this.addSubAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(RavenousAbilityCondition.instance).setRuleVisible(false));
    }

    private RavenousAbility(final RavenousAbility ability) {
        super(ability);
    }

    @Override
    public RavenousAbility copy() {
        return new RavenousAbility(this);
    }

    @Override
    public String getRule() {
        return "Ravenous <i>(This creature enters the battlefield with X +1/+1 counters on it. " +
                "If X is 5 or more, draw a card when it enters.)</i>";
    }
}

enum RavenousAbilityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return GetXValue.instance.calculate(game, source, null) >= 5;
    }

    @Override
    public String toString() {
        return "X is 5 or more";
    }
}
