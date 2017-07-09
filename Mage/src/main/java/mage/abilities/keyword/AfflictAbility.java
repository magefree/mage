package mage.abilities.keyword;

import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;

public class AfflictAbility extends BecomesBlockedTriggeredAbility {

    private int lifeLoss;

    @Override
    public AfflictAbility copy() {
        return new AfflictAbility(this);
    }

    public AfflictAbility(int amount) {
        super(new LoseLifeDefendingPlayerEffect(amount, true)
                .setText("Afflict " + amount + " <i>(Whenever this creature becomes blocked, defending player loses " + amount + " life.)</i>"), false);
        lifeLoss = amount;
    }

    public AfflictAbility(final AfflictAbility afflictAbility) {
        super(afflictAbility);
        lifeLoss = afflictAbility.lifeLoss;
    }

    @Override
    public String getRule() {
        return "Afflict " + lifeLoss + " <i>(Whenever this creature becomes blocked, defending player loses " + lifeLoss + " life.)</i>";
    }
}
