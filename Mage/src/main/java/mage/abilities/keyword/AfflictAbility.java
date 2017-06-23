package mage.abilities.keyword;

import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;

public class AfflictAbility extends BecomesBlockedTriggeredAbility {

    private int lifeLoss;

    @Override
    public AfflictAbility copy() {
        return new AfflictAbility(this);
    }

    public AfflictAbility(int amount){
        super(new LoseLifeDefendingPlayerEffect(amount, true), false);
    }

    public AfflictAbility(final AfflictAbility afflictAbility){
        super(afflictAbility);
        lifeLoss = afflictAbility.lifeLoss;
    }

}
