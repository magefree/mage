package mage.abilities.keyword;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class ExhaustAbility extends ActivatedAbilityImpl {

    public ExhaustAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    private ExhaustAbility(final ExhaustAbility ability) {
        super(ability);
        this.maxActivationsPerGame = 1;
    }

    @Override
    public ExhaustAbility copy() {
        return new ExhaustAbility(this);
    }

    @Override
    public String getRule() {
        return "Exhaust &mdash; " + super.getRule() + " <i>(Activate each exhaust ability only once.)</i>";
    }
}
