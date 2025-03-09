package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class ExhaustAbility extends ActivatedAbilityImpl {

    private boolean withReminderText = true;

    public ExhaustAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    public ExhaustAbility(Effect effect, Cost cost, boolean withReminderText) {
        super(Zone.BATTLEFIELD, effect, cost);
        this.setRuleVisible(false);
        this.withReminderText = withReminderText;
    }

    private ExhaustAbility(final ExhaustAbility ability) {
        super(ability);
        this.maxActivationsPerGame = 1;
        this.withReminderText = ability.withReminderText;
    }

    public ExhaustAbility withReminderText(boolean withReminderText) {
        this.withReminderText = withReminderText;
        return this;
    }

    @Override
    public ExhaustAbility copy() {
        return new ExhaustAbility(this);
    }

    @Override
    public String getRule() {
        return "Exhaust &mdash; " + super.getRule() +
                (withReminderText ? " <i>(Activate each exhaust ability only once.)</i>" : "");
    }
}
