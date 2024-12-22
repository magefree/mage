package mage.abilities.keyword;

import java.util.UUID;

import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class AfflictAbility extends BecomesBlockedSourceTriggeredAbility {

    private final int lifeLoss;

    @Override
    public AfflictAbility copy() {
        return new AfflictAbility(this);
    }

    public AfflictAbility(int amount) {
        super(new LoseLifeTargetEffect(amount)
                .setText("Afflict " + amount + " <i>(Whenever this creature becomes blocked, defending player loses " + amount + " life.)</i>"), false);
        lifeLoss = amount;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            UUID defenderId = game.getCombat().getDefendingPlayerId(getSourceId(), game);
            if (defenderId != null) {
                this.getEffects().setTargetPointer(new FixedTarget(defenderId));
                return true;
            }
        }
        return false;
    }

    protected AfflictAbility(final AfflictAbility afflictAbility) {
        super(afflictAbility);
        lifeLoss = afflictAbility.lifeLoss;
    }

    @Override
    public String getRule() {
        return "Afflict " + lifeLoss + " <i>(Whenever this creature becomes blocked, defending player loses " + lifeLoss + " life.)</i>";
    }
}
